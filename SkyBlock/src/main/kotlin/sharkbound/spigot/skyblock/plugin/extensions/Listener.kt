package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.event.Listener
import sharkbound.spigot.skyblock.plugin.pluginManager
import sharkbound.spigot.skyblock.plugin.skyBlockInstance

fun <T : Listener> T.registerEvents() {
    pluginManager.registerEvents(
        this,
        skyBlockInstance
    )
}