package sharkbound.spigot.miscplugin.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand
import sharkbound.spigot.miscplugin.shared.extensions.send

object CommandTest : CommandExecutor {
    init {
        registerCommand("t")
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        sender.inventory.addItem(buildItem(Material.STICK) {
            name = "&3whacking stick"
            amount = 10

            enchant(Enchantment.FIRE_ASPECT, 1000)

            nbt {
                setString("type", "whackingstick")
            }
        })
        return false
    }
}