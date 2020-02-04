package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.inventories.GuiButtons
import sharkbound.spigot.skyblock.plugin.inventories.GuiNames
import sharkbound.spigot.skyblock.plugin.inventories.SkyBlockMainGui

class InventoryListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onItemClicked(e: InventoryClickEvent) {
        val caller = e.whoClicked as? Player ?: return

        if (e.clickedInventory.name !in GuiNames.allNames) {
            return
        }

        e.currentItem.itemMeta.displayName.also {
            if (it !in GuiButtons.allNames) {
                return
            }

            e.cancel()
            SkyBlockMainGui.clicked(caller, it)
        }

    }
}