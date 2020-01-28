package sharkbound.spigot.skyblock.extensions

import org.bukkit.event.Listener
import sharkbound.spigot.skyblock.pluginManager
import sharkbound.spigot.skyblock.skyBlockInstance

fun <T : Listener> T.register() {
    pluginManager.registerEvents(
        this,
        skyBlockInstance
    )
}