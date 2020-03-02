package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import sharkbound.spigot.miscplugin.items.ShulkerPortal
import sharkbound.spigot.miscplugin.shared.commands.BaseCommand
import sharkbound.spigot.miscplugin.shared.extensions.isPlayer
import sharkbound.spigot.miscplugin.shared.extensions.replaceHeldItem
import kotlin.contracts.ExperimentalContracts

object CommandShulkerPortal : BaseCommand("shulkerportal") {

    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender.isPlayer()) {
            sender replaceHeldItem ShulkerPortal.create()
        }
        return false
    }
}