package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.event.Listener
import sharkbound.spigot.miscplugin.shared.instance
import sharkbound.spigot.miscplugin.shared.manager

fun Listener.registerEvents() {
    manager.registerEvents(this, instance)
}