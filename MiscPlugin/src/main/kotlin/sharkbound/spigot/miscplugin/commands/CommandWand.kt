package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.miscplugin.items.ArrowWand
import sharkbound.spigot.miscplugin.items.PhantomPortal
import sharkbound.spigot.miscplugin.items.ShulkerPortal
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.extensions.*
import kotlin.contracts.ExperimentalContracts

// todo mob speed?
object CommandWand : BaseCommand("wand") {

    private const val SHULKER_PORTAL = "shulkerportal"
    private const val SHULKER_WAND = "shulkerwand"
    private const val PHANTOM_PORTAL = "phantomportal"
    private const val ARROW_WAND = "arrowwand"

    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isPlayer()) return false

        if (args.len == 0) {
            sender.send("&e/wand <$SHULKER_PORTAL | $SHULKER_WAND | $PHANTOM_PORTAL | $ARROW_WAND>")
            return false
        }

        sender replaceHeldItem (
                when (args[0]) {
                    SHULKER_PORTAL -> ShulkerPortal.create()
                    SHULKER_WAND -> ShulkerWand.create()
                    PHANTOM_PORTAL -> PhantomPortal.create()
                    ARROW_WAND -> ArrowWand.create()
                    else -> return false
                })

        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> =
        sequenceOf(SHULKER_PORTAL, SHULKER_WAND, PHANTOM_PORTAL, ARROW_WAND).filter { args[0] in it }.toMutableList()
}