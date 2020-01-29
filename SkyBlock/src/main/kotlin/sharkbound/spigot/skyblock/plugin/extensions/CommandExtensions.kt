package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.command.CommandExecutor
import sharkbound.spigot.skyblock.plugin.skyBlockInstance

fun CommandExecutor.register(name: String) {
    skyBlockInstance.getCommand(name).executor = this
}