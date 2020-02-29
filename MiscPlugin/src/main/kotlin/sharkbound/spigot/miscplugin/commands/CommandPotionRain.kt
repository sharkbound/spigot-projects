package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import sharkbound.spigot.miscplugin.shared.extensions.isPlayer
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand
import sharkbound.spigot.miscplugin.shared.extensions.send
import sharkbound.spigot.miscplugin.shared.server
import kotlin.contracts.ExperimentalContracts

object CommandPotionRain : BaseCommand("potionrain") {
    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isPlayer()) return false

        sender.send("&aHI")
        return false
    }
}