package sharkbound.spigot.miscplugin.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.builders.buildItem
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
        if (sender.isPlayer()) {
            sender.inventory.addItem(
                buildItem(Material.DIAMOND_AXE) {
                    name = "&5Dragon Killer"
                    enchant(Enchantment.DAMAGE_ALL, 10000)
                }
            )

            withTags {
                sender.world.livingEntities
                    .asSequence()
                    .filter { it !is Player }
                    .forEach {
                        it.modifyNBT {
                            getList(attributes)?.findByName(movementSpeed)?.apply {
                                setDouble(base, .09)
                            }
                        }
                    }
            }
//            sender.world.spawnEntity(sender.location, EntityType.WITHER_SKELETON).nbt {
//                withTags {
//                    getList(attributes)?.findByName(movementSpeed)?.apply {
//                        println(toString())
//                        setDouble(base, 3.0)
//                    }
//                }
//            }
        }

        return false
    }
}