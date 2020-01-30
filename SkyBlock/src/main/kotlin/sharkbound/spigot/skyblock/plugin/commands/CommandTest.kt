package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.extensions.send

class CommandTest : CommandExecutor {
    init {
        register("test")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            return false
        }

        caller.send("hello!")
        return false
    }
}