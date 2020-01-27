package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.skyblock.*
import sharkbound.spigot.skyblock.extensions.*
import sharkbound.spigot.skyblock.utils.cannotBeCalledFromConsole
import sharkbound.spigot.skyblock.utils.createSkyBlockWorld
import sharkbound.spigot.skyblock.utils.getWorld
import sharkbound.spigot.skyblock.utils.worldExists

class CommandSkyBlock : CommandExecutor, TabCompleter {
    init {
        register("skyblock")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            return cannotBeCalledFromConsole()
        }

        if (args.len != 1) {
            caller.sendMessage("required args: <join | reset>")
            return false
        }

        when (args[0]) {
            "join" -> {
                if (!worldExists(caller.skyBlockWorldName)) {
                    createSkyBlockWorld(caller)
                }
                caller.teleport(
                    Location(
                        getWorld(caller.skyBlockWorldName),
                        0.0,
                        Coords.SKY_ISLAND_SCHEMATIC_Y + 10,
                        0.0,
                        caller.location.yaw,
                        caller.location.pitch
                    )
                )
            }
            "reset" -> {
                caller.skyBlockWorld?.delete()
                createSkyBlockWorld(caller)
                caller.sendColored("&ayour skyblock world as been reset!")
            }
            "del" -> {
                caller.skyBlockWorld?.delete()
                caller.sendColored("&ayour skyblock world as been deleted!")
            }
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender?,
        command: Command?,
        alias: String?,
        args: Array<out String>
    ): MutableList<String> {
        return mutableListOf("join", "reset")
    }
}