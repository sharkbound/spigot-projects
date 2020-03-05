package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
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
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
        }.applyNBT()
}

object MovingWandListener : BaseListener() {
    @EventHandler
    fun interact(e: PlayerInteractEvent) {
        e.player.apply {
            if (WandUtil.idFrom(inventory.itemInMainHand) != MovingWand.nbtId) {
                return
            }

            val offset = 10
            val look = target()

            world.livingEntities.filter { it.location dist look <= 10 }
                .forEach { it.teleport(it.location.clone { y += offset * 2 + 2 }) }

            look.locationsInRadius(10).filter { it.block.type.isBlock }.forEach {
                it.clone { y += offset }.block.type = it.block.type
                it.block.type = Material.AIR
            }

        }
    }
}