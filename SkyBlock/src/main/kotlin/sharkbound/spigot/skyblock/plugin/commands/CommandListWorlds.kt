package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import sharkbound.spigot.skyblock.plugin.allWorlds
import sharkbound.spigot.skyblock.plugin.extensions.register

class CommandListWorlds : CommandExecutor {
    init {
        register("listworlds")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        allWorlds.forEach {
            caller.sendMessage("NAME: ${it.name}, UID: ${it.uid}")
        }
        return false
    }
}