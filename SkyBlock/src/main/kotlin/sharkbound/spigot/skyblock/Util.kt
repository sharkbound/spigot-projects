package sharkbound.spigot.skyblock

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import sharkbound.commonutils.extensions.use
import sharkbound.spigot.skyblock.plugin.commands.CommandDTP
import sharkbound.spigot.skyblock.plugin.commands.CommandListWorlds
import sharkbound.spigot.skyblock.plugin.commands.CommandSkyBlock
import sharkbound.spigot.skyblock.plugin.commands.CommandStop
import sharkbound.spigot.skyblock.plugin.listeners.TntListener
import java.lang.Exception
import java.nio.file.Files

val allCommands = mutableListOf<CommandExecutor>()
val allEventListeners = mutableListOf<Listener>()

fun registerAllCommands() {
    allCommands.addAll(
        listOf(
            CommandSkyBlock(),
            CommandStop(),
            CommandDTP(),
            CommandListWorlds()
        )
    )
}

fun registerAllEventListeners() {
    allEventListeners += TntListener()
}

fun colorFormat(message: String, char: Char = '&') =
    ChatColor.translateAlternateColorCodes(char, message)

fun deleteWorld(worldName: String): Boolean {
    getWorld(worldName) use {
        Bukkit.unloadWorld(this, false)
        try {
            deleteWorld(worldFolder)
        } catch (e: Exception) {
            println("failed to delete world: $name")
            e.printStackTrace()
        }
        return true
    }
    return false
}