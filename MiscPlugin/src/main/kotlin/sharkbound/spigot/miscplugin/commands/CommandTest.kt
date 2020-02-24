package sharkbound.spigot.miscplugin.commands

import net.minecraft.server.v1_14_R1.AttributeModifier
import net.minecraft.server.v1_14_R1.Slot
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemFlag
import sharkbound.spigot.miscplugin.shared.builders.ItemBuilder
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.enums.buildNBTCompound
import sharkbound.spigot.miscplugin.shared.enums.buildNBTTagList
import sharkbound.spigot.miscplugin.shared.enums.withTags
import sharkbound.spigot.miscplugin.shared.extensions.*
import kotlin.contracts.ExperimentalContracts

// todo mob speed?
object CommandTest : CommandExecutor {
    init {
        registerCommand("t")
    }

    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isPlayer()) return false

        val item = buildItem(Material.DIAMOND_SWORD) {
            name = "&agem of power"
            genericAttackDamage(9.0, ItemBuilder.Slot.MainHand)
        }

        sender.inventory.addItem(item)

        withTags {
            sender.world.livingEntities
                .asSequence()
                .filter { it !is Player }
                .forEach {
                    it.modifyNBT {
                        getList(attributes)?.findByName(genericMovementSpeed)?.apply {
                            setDouble(base, .09)
                        }
                    }
                }
        }

        return false
    }
}