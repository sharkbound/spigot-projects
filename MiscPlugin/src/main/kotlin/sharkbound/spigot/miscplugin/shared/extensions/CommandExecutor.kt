package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.command.CommandExecutor
import sharkbound.spigot.miscplugin.shared.instance

fun CommandExecutor.registerCommand(cmd: String) {
    instance.getCommand(cmd)!!.setExecutor(this)
}