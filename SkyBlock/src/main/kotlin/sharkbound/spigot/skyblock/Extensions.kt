package sharkbound.spigot.skyblock

import org.bukkit.command.CommandExecutor
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

fun CommandExecutor.register(name: String) {
    plugin.getCommand(name).executor = this
}

fun <T : Listener> T.register() {
    pluginManager.registerEvents(this, plugin)
}