package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.extensions.isPlayer
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand
import sharkbound.spigot.miscplugin.shared.extensions.replaceHeldItem
import kotlin.contracts.ExperimentalContracts

object CommandShulkerWand : CommandExecutor {
    init {
        registerCommand("shulkerwand")
    }

    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.isPlayer()) {
            sender.replaceHeldItem(ShulkerWand.create())
        }
        return false
    }
}