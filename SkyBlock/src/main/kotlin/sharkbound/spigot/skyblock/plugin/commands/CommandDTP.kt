package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.skyblock.*

class CommandDTP : CommandExecutor, TabCompleter {
    init {
        register("dtp")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            return cannotBeCalledFromConsole()
        }

        if (args.len != 1) {
            return args.wrongArgsLength(1, usage = "<world name>")
        }

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
        val worldNames = allWorlds.asSequence().map { it.name }

        return when {
            args.isEmpty() -> worldNames.toMutableList()
            else -> worldNames.filterContainsSubstring(args[0]).toMutableList()
        }

    }
}