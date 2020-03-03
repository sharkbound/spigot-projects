package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.miscplugin.items.*
import sharkbound.spigot.miscplugin.shared.commands.BaseCommand
import sharkbound.spigot.miscplugin.shared.extensions.*
import kotlin.contracts.ExperimentalContracts

// todo mob speed?
object CommandWand : BaseCommand("wand") {
    val nameToItem = mapOf(
        "shulkerportal" to { ShulkerPortal.create() },
        "shulker" to { ShulkerWand.create() },
        "phantomportal" to { PhantomPortal.create() },
        "arrow" to { ArrowWand.create() },
        "testingwand" to { TestingWand.create() }
    )

    @ExperimentalContracts
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (!sender.isPlayer()) return false
        if (args.len == 0) {
            sender.send("&e/wand <${nameToItem.keys.joinToString(" | ")}>")
            return false
        }

        nameToItem[args[0]]?.let {
            sender replaceHeldItem it()
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> =
        nameToItem.keys.filter { args[0] in it }.toMutableList()
}