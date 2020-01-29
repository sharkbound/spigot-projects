package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.allWorldNames
import sharkbound.spigot.skyblock.plugin.extensions.filterContainsSubstring
import sharkbound.spigot.skyblock.plugin.extensions.isLenLessThan
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.utils.cannotBeCalledFromConsole
import sharkbound.spigot.skyblock.plugin.utils.getWorld
import sharkbound.spigot.skyblock.plugin.utils.wrongArgsLength

class CommandDTP : CommandExecutor, TabCompleter {
    init {
        register("dtp")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            return cannotBeCalledFromConsole()
        }

        if (args isLenLessThan 1) {
            return args.wrongArgsLength(caller, 1, usage = "<world name>")
        }

        caller.location.apply {
            caller.teleport(
                Location(getWorld(args[0]), x, y, z, yaw, pitch)
            )
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>
    ): MutableList<String> =
        when {
            args.isEmpty() -> allWorldNames
            else -> allWorldNames filterContainsSubstring args[0]
        }.toMutableList()
}