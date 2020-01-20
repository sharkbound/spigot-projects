package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.WorldCreator
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.skyblock.deleteWorld
import sharkbound.spigot.skyblock.getWorld
import sharkbound.spigot.skyblock.plugin.generators.SkyBlockChunkGenerator
import sharkbound.spigot.skyblock.register
import sharkbound.spigot.skyblock.sendColored

class CommandSkyBlock : CommandExecutor, TabCompleter {
    init {
        register("skyblock")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) return false
        if (args.len != 2) {
            caller.sendMessage("required args: <join | create> <name>")
            return false
        }

        val (mode, name) = args.map { it.toLowerCase() }
        when (mode) {
            "create" -> Bukkit.createWorld(WorldCreator(name).generator(SkyBlockChunkGenerator()))
            "join" -> caller.teleport(
                Location(
                    getWorld(name),
                    0.0,
                    200.0,
                    0.0,
                    caller.location.yaw,
                    caller.location.pitch
                )
            )
            "delete" -> {
                if (deleteWorld(name)) {
                    caller.sendColored("&adeleted world \"$name\"")
                } else {
                    caller.sendColored("&4failed to delete world \"$name\"")
                }

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
        if (args.len == 1) {
            return mutableListOf("create", "join", "delete")
        }
        return mutableListOf()
    }
}