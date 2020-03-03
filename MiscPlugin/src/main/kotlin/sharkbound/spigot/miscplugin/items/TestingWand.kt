package sharkbound.spigot.miscplugin.items

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.TNTPrimed
import org.bukkit.entity.Wolf
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.commonutils.extensions.filterIfNotNull
import sharkbound.commonutils.util.randBoolean
import sharkbound.commonutils.util.randDouble
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.shared.utils.TickUnit
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.ticks
import sharkbound.spigot.miscplugin.shared.utils.vector

object TestingWand {
    const val nbtId = "testingwand"

    fun create() =
        buildItem(Material.STICK) {
            name = "&2Testing Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
            nbt {
                setString("type", nbtId)
            }
        }
}

object TestingWandListener : BaseListener() {
    @EventHandler
    fun onFireWandInteract(e: PlayerInteractEvent) {
        val fireBallRange = 60
        e.player.apply {
            if (inventory.itemInMainHand.nbt.getString("type") != TestingWand.nbtId)
                return

            val dir = direction.normalize()
            val origin = eyeLocation.clone()
            var loc = origin.clone()

            cancellingRepeatingSyncTask(intervalTicks = 1) {
                loc = loc.add(dir.clone().multiply(2))
                spawnParticle(Particle.LAVA, loc, 1)

                loc.chunk.entities.living().filter {
                    it.uniqueId != uniqueId && it.location dist loc <= 5
                }.forEach { it.fireTicks = 20 * 20 }

                loc.blocksInRadius(5).filter { it.type.isFlammable && randDouble(0.0, 1.0) < .02 }.forEach {
                    it.location.add(1.y)
                        .block
                        .type = Material.FIRE
                }
                loc.block.filterIfNotNull { it.type.isFlammable }
                    ?.let { it.location.add(1.y).block.type = Material.FIRE }

                if (origin dist loc > fireBallRange) {
                    cancel()
                }
            }
        }
    }
}