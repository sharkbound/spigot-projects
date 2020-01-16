package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.SkyBlock

open class HelloWorld(val plugin: SkyBlock) : CommandExecutor {
    init {
        plugin.getCommand("hello").executor = this
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            caller.sendMessage("only players can call this command")
            return true
        }

        caller.sendMessage("hello, ${caller.displayName}!")
        return false
    }
}