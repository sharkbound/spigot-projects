package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import sharkbound.spigot.skyblock.plugin.extensions.normalized
import sharkbound.spigot.skyblock.plugin.extensions.registerEvents

class InventoryGuiClickListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onItemClicked(e: InventoryClickEvent) {
        allGui.values
            .firstOrNull { it.normalizedName == e.inventory?.name?.normalized }
            ?.handleClickedEvent(e)
    }
}