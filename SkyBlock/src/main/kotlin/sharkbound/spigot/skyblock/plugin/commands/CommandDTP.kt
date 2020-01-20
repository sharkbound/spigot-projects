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
        println("\n----------------------------\nDIM TP COMMAND\n${javaClass.name}\nDISABLE AFTER DONE\n----------------------------")
        register("dtp")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player || args.len != 2)
            return true

        caller.location.apply {
            caller.teleport(
                Location(
                    when (args[0]) {
                        "uid" -> getWorld(UUID.fromString(args[1]))
                        "name" -> getWorld(args[1])
                        else -> {
                            caller.sendMessage("bad argument: ${args[0]}")
                            return false
                        }
                    }, x, y, z, yaw, pitch
                )
            )
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>
    ): MutableList<String> {
        if (args.len == 1) return mutableListOf("uid", "name")
        if (args.len == 2)
            return allWorlds.asSequence().map { it.name }.toMutableList()
        return mutableListOf()
    }


}