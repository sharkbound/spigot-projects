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

    private const val SHULKER_PORTAL = "shulkerportal"
    private const val SHULKER_WAND = "shulker"
    private const val PHANTOM_PORTAL = "phantomportal"
    private const val ARROW_WAND = "arrow"
    private const val LEVITATION_WAND = "levitation"

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
                    LEVITATION_WAND -> LevitationWand.create()
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
        sequenceOf(SHULKER_PORTAL, SHULKER_WAND, PHANTOM_PORTAL, ARROW_WAND, LEVITATION_WAND)
            .filter { args[0] in it }.toMutableList()
}