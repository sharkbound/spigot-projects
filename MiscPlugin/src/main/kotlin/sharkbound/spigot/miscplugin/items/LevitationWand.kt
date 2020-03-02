package sharkbound.spigot.miscplugin.items

import net.minecraft.server.v1_14_R1.RayTrace
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import sharkbound.commonutils.extensions.defaultIfNull
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.delaySyncTask
import sharkbound.spigot.miscplugin.shared.utils.vector
import kotlin.math.roundToInt

object LevitationWand {
    const val nbtId = "levitationwand"

    fun create() =
        buildItem(Material.STICK) {
            name = "&2Levitation Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
            nbt {
                setString("type", nbtId)
            }
        }
}

object LevitationListener : BaseListener() {
    @EventHandler
    fun onLevitationWandInteract(e: PlayerInteractEvent) {
        e.player.apply {
            if (inventory.itemInMainHand.nbt.getString("type") != LevitationWand.nbtId)
                return

            val dir = direction.normalize()
            val origin = eyeLocation.clone()
            var loc = origin.clone()
            cancellingRepeatingSyncTask(intervalTicks = 10) {
                spawnParticle(Particle.HEART, loc, 1)
                loc.chunk.entities.living()
                    .firstOrNull { it.location dist loc < 3 }
                    ?.run { addPotionEffect(PotionEffect(PotionEffectType.LEVITATION, 100, 2)) }
                if (location dist loc > 120 || world.getBlockAt(loc).type.isOccluding) {
                    cancel()
                }
                loc = loc.add(dir.clone().multiply(1))
            }
        }
    }
}