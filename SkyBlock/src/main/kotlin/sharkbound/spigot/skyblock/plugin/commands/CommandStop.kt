package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import sharkbound.spigot.skyblock.extensions.register

class CommandStop : CommandExecutor {
    init {
        println("\n----------------------------\nQUICK SHUTDOWN COMMAND\n${javaClass.name}\nDISABLE AFTER DONE\n----------------------------")
        register("s")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        Bukkit.shutdown()
        return false
    }
}