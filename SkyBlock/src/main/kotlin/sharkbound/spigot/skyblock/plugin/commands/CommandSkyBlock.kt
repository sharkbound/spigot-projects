package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.inventories.SkyBlockMainGui
import sharkbound.spigot.skyblock.plugin.utils.cannotBeCalledFromConsole

class CommandSkyBlock : CommandExecutor/*, TabCompleter*/ {
    init {
        register("skyblock")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            return cannotBeCalledFromConsole()
        }

        SkyBlockMainGui.show(caller)
        return true

//        when (args[0].toLowerCase()) {
//            "join" -> {
//                if (caller.skyBlockWorld == null) {
//                    createSkyBlockWorld(caller)
//                }
//                caller.teleport(
//                    Location(
//                        caller.skyBlockWorld,
//                        0.0,
//                        Coords.SKY_ISLAND_SCHEMATIC_Y,
//                        0.0,
//                        caller.location.yaw,
//                        caller.location.pitch
//                    ).add(Coords.skyIslandSpawnOffset)
//                )
//            }
//            "reset" -> {
//                caller.skyBlockWorld?.delete()
//                createSkyBlockWorld(caller)
//                caller.sendColored("&ayour skyblock world as been reset!")
//            }
//            "del" -> {
//                if (caller.skyBlockWorld?.players?.isEmpty() == true) {
//
//                    return false
//                }
//
//                caller.skyBlockWorld?.delete()
//                caller.sendColored("&ayour skyblock world as been deleted!")
//            }
//        }

    }

//    private val options = mutableListOf("join", "reset", "del")

//    override fun onTabComplete(
//        sender: CommandSender?,
//        command: Command?,
//        alias: String?,
//        args: Array<out String>
//    ): MutableList<String> =
//        when (args.len) {
//            0 -> options
//            else -> options.filterContainsSubstring(args[0]).toMutableList()
//        }
}