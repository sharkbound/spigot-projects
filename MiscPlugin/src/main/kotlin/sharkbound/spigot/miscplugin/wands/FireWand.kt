package sharkbound.spigot.miscplugin.wands

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.commonutils.util.randDouble
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.utils.WandUtil
import sharkbound.spigot.miscplugin.utils.showParticle

object FireWand : Wand {
    override val nbtId = "firewand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&4Fire Wand"
            setWandData()
        }
}

object FireWandListener : BaseListener() {
    private const val FIRE_CHANCE = .02
    const val burstDamage = 10.0
    const val burstRange = 10.0
    const val burnTime = 20 * 2
    const val fireBallRange = 60

    @EventHandler
    fun onFireWandInteract(e: PlayerInteractEvent) {
        e.player.apply {
            if (WandUtil.idFrom(inventory.itemInMainHand) != FireWand.nbtId)
                return

            val dir = direction
            val origin = eyeLocation.clone()
            var loc = origin.clone()

            cancellingRepeatingSyncTask(intervalTicks = 2) {
                loc = loc.add(dir.clone { multiply(2) })
                showParticle(ParticleEffect.FLAME, loc, 1, .05)

                loc.chunk.entities.living().filter {
                    it.uniqueId != uniqueId && it.location dist loc <= 5
                }.forEach { it.fireTicks = burnTime }

                loc.blocksInRadius(5).filter { it.type.isFlammable && randDouble(0.0, 1.0) < FIRE_CHANCE }.forEach {
                    it.getRelative(BlockFace.UP).type = Material.FIRE
                }

                if (loc.block.type != Material.AIR || origin dist loc > fireBallRange) {
                    showParticle(ParticleEffect.FLAME, loc.subtract(dir.multiply(1.5)), 30, .25)
                    loc.world?.livingEntities?.filter { it.id != id && it.location dist loc <= burstRange }
                        ?.forEach {
                            it.damage(burstDamage)
                            it.fireTicks = burnTime
                        }
                    cancel()
                }
            }
        }
    }
}