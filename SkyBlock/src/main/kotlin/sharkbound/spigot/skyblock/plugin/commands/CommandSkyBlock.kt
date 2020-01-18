package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import sharkbound.spigot.skyblock.register

class CommandSkyBlock : CommandExecutor {
    init {
        register("skyblock")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        caller.sendMessage("hello :)")
        return false
    }
}