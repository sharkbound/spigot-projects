package sharkbound.spigot.miscplugin.items

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.TNTPrimed
import org.bukkit.entity.Wolf
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
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
        val fireBallRange = 50
        e.player.apply {
            if (inventory.itemInMainHand.nbt.getString("type") != TestingWand.nbtId)
                return

            val dir = direction.normalize()
            val origin = eyeLocation.clone()
            var loc = origin.clone()
            val mats = setOf(
                Material.DIRT, Material.STONE, Material.GRAVEL, Material.GRANITE, Material.DIORITE,
                Material.ANDESITE, Material.DIRT, Material.WATER, Material.LAVA
            )

            cancellingRepeatingSyncTask(intervalTicks = 0) {
                loc = loc.add(dir.clone().multiply(1))
                loc.blocksInRadius(10).filter { it.type in mats }.forEach {
                    it.type = Material.AIR
                }
                if (origin dist loc > fireBallRange) {
                    cancel()
                }
            }
        }
    }
}