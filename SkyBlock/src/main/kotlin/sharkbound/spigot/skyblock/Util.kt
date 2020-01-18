package sharkbound.spigot.skyblock

import org.bukkit.ChatColor
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import sharkbound.spigot.skyblock.plugin.commands.CommandSkyBlock
import sharkbound.spigot.skyblock.plugin.commands.CommandStop
import sharkbound.spigot.skyblock.plugin.listeners.JoinListener

val allCommands = mutableListOf<CommandExecutor>()
val allEventListeners = mutableListOf<Listener>()

fun registerAllCommands() {
    allCommands += CommandSkyBlock()
    allCommands += CommandStop()
}

fun registerAllEventListeners() {
    allEventListeners += JoinListener()
}

fun colorFormat(message: String, char: Char = '&') =
    ChatColor.translateAlternateColorCodes(char, message)