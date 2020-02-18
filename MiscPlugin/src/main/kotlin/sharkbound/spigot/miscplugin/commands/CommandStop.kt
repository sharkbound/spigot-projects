package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.ext.registerCommand
import sharkbound.spigot.miscplugin.shared.server

object CommandStop : CommandExecutor {
    init {
        registerCommand("s")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        server.shutdown()
        return false
    }
}