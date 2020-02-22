package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.extensions.noAI
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand

object CommandTest : CommandExecutor {
    init {
        registerCommand("t")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        sender.world.spawnEntity(sender.location, EntityType.CREEPER).noAI().isInvulnerable = true

        return false
    }
}