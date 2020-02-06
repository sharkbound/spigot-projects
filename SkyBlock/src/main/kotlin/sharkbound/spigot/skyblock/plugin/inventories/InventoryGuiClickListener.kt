package sharkbound.spigot.skyblock.plugin.inventories

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import sharkbound.spigot.skyblock.plugin.extensions.*

class InventoryGuiClickListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onItemClicked(e: InventoryClickEvent) {
        allGui.values.forEach {
            if (it.normalizedName == e.inventory?.name?.normalized && it.handleClickedEvent(e)) {
                return
            }
        }
    }

}