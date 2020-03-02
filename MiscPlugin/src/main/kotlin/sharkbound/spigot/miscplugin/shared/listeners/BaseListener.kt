package sharkbound.spigot.miscplugin.shared.listeners

import org.bukkit.event.Listener
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents

open class BaseListener : Listener {
    init {
        registerEvents()
    }
}