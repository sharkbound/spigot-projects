package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.utils.WandUtil

object MovingWand : Wand {
    override val nbtId = "movingwand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&2Moving Wand"
            setWandData()
        }
}

object MovingWandListener : BaseListener() {
    val radius = 20

    @EventHandler
    fun interact(e: PlayerInteractEvent) {
        e.player.apply {
            if (WandUtil.idFrom(inventory.itemInMainHand) != MovingWand.nbtId) {
                return
            }

            val look = target()

            look.locationsInRadius(radius).filter { it.block.type.isBlock }.sortedByDescending { it.y }.forEach {
                if (it.block.type != Material.AIR) {
                    it.clone { y += radius }.block.type = it.block.type
                    it.block.type = Material.AIR
                }
            }

        }
    }
}