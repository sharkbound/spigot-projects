package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import sharkbound.commonutils.extensions.choice
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.inventories.GuiButtons
import sharkbound.spigot.skyblock.plugin.inventories.GuiNames
import sharkbound.spigot.skyblock.plugin.inventories.SkyBlockGui
import sharkbound.spigot.skyblock.plugin.server

class InventoryListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onItemClicked(e: InventoryClickEvent) {
        val caller = e.whoClicked as? Player ?: return
        if (e.clickedInventory.name !in GuiNames.allNames || e.currentItem.itemMeta.displayName !in GuiButtons.allNames) {
            return
        }

        e.cancel()
        SkyBlockGui.clicked(caller, e.currentItem.itemMeta.displayName)
    }

    @EventHandler
    fun onItemHeld(e: PlayerItemHeldEvent) {
        try {
            println("old: ${e.hasLastItem} new: ${e.hasNewItem}")
            if (e.newItem?.displayNameIs("boom") == true) {
                e.player.world.createExplosion(e.player.lookLocation, 10f)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}