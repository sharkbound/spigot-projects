package sharkbound.spigot.miscplugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.miscplugin.shared.ext.registerEvents
import sharkbound.spigot.miscplugin.shared.ext.send

object PlayerListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        println(e.player.send(e.player.inventory.itemInMainHand))
    }
}