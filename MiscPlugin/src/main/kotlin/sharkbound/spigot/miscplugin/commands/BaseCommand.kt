package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand

open class BaseCommand(val cmd: String) : CommandExecutor, TabCompleter {
    init {
        registerCommand(cmd)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        TODO("not implemented")
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> =
        mutableListOf()
}