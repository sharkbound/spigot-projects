package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import sharkbound.spigot.skyblock.plugin.extensions.cancel
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.inventories.GuiButtons
import sharkbound.spigot.skyblock.plugin.inventories.GuiNames
import sharkbound.spigot.skyblock.plugin.inventories.SkyBlockGui

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
}