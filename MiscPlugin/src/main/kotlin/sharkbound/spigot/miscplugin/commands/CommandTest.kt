package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand
import sharkbound.spigot.miscplugin.shared.extensions.send

object CommandTest : CommandExecutor {
    init {
        registerCommand("test")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        sender.send("&ahello ${sender.name}!")
        return false
    }
}