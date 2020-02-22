package sharkbound.spigot.miscplugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents

object PlayerListener : Listener {
    init {
        registerEvents()
    }
}