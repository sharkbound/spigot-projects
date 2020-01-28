package sharkbound.spigot.skyblock.extensions

import org.bukkit.command.CommandExecutor
import sharkbound.spigot.skyblock.skyBlockInstance

fun CommandExecutor.register(name: String) {
    skyBlockInstance.getCommand(name).executor = this
}