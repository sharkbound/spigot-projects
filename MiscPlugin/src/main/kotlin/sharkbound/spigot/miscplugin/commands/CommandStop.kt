package sharkbound.spigot.miscplugin.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import sharkbound.spigot.miscplugin.shared.commands.BaseCommand
import sharkbound.spigot.miscplugin.shared.server

object CommandStop : BaseCommand("s") {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        server.shutdown()
        return false
    }
}