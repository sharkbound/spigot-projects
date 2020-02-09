package sharkbound.spigot.skyblock.plugin.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.extensions.copyWithNBT
import sharkbound.spigot.skyblock.plugin.extensions.nms
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.utils.newStack


class CommandTest : CommandExecutor {
    init {
        register("test")
    }

    override fun onCommand(caller: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (caller !is Player) {
            return false
        }

        caller.inventory.addItem(buildItem {
            material(Material.WOOD_SWORD)
            nbt {

            }
        })
        return false
    }

    private fun nbtTest(caller: Player) {
        try {
            val i = newStack(Material.DIAMOND_BLOCK).copyWithNBT {
                setString("test", "5")
                println(this)
            }
            println(i.nms.tag)
            caller.inventory.addItem(i)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}