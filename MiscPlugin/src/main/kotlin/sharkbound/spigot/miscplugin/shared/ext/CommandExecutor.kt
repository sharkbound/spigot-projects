package sharkbound.spigot.miscplugin.shared.ext

import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import sharkbound.spigot.miscplugin.shared.instance
import sharkbound.spigot.miscplugin.shared.manager

fun CommandExecutor.registerCommand(cmd: String) {
    instance.getCommand(cmd)!!.setExecutor(this)
}