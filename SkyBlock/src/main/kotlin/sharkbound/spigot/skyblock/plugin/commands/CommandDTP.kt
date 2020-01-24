package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.skyblock.getWorld
import sharkbound.spigot.skyblock.register
import sharkbound.spigot.skyblock.allWorlds
import java.util.*

class CommandDTP : CommandExecutor, TabCompleter {
    init {
//        println("\n----------------------------\nDIM TP COMMAND\n${javaClass.name}\nDISABLE AFTER DONE\n----------------------------")
        register("dtp")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player || args.len != 1)
            return true

        caller.location.apply {
            caller.teleport(
                Location(getWorld(args[0]), x, y, z, yaw, pitch)
            )
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>
    ): MutableList<String> {
        return allWorlds.asSequence().map { it.name }.toMutableList()
    }


}