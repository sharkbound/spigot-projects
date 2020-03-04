package sharkbound.spigot.miscplugin.items

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.commonutils.extensions.filterIfNotNull
import sharkbound.commonutils.util.randDouble
import sharkbound.spigot.miscplugin.extensions.spawn
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.utils.showParticle

object FireWand : Wand {
    override val nbtId = "firewand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&4Fire Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
            nbt {
                setString("type", nbtId)
            }
        }
}

object FireWandListener : BaseListener() {
    @EventHandler
    fun onFireWandInteract(e: PlayerInteractEvent) {
        val fireBallRange = 60
        e.player.apply {
            if (inventory.itemInMainHand.nbt.getString("type") != FireWand.nbtId)
                return

            val dir = direction
            val origin = eyeLocation.clone()
            var loc = origin.clone()

            cancellingRepeatingSyncTask(intervalTicks = 2) {
                loc = loc.add(dir.clone().multiply(2))
                showParticle(ParticleEffect.FLAME, loc, 1, .05)

                loc.chunk.entities.living().filter {
                    it.uniqueId != uniqueId && it.location dist loc <= 5
                }.forEach { it.fireTicks = 20 * 20 }

                loc.blocksInRadius(5).filter { it.type.isFlammable && randDouble(0.0, 1.0) < .02 }.forEach {
                    it.getRelative(BlockFace.UP).type = Material.FIRE
                }

                if (!loc.block.type.isAir || origin dist loc > fireBallRange) {
                    showParticle(ParticleEffect.FLAME, loc.subtract(dir.multiply(2)), 30, .25)
                    cancel()
                }
            }
        }
    }
}