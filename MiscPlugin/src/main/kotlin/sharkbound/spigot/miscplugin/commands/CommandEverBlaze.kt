package sharkbound.spigot.miscplugin.commands

import net.minecraft.server.v1_14_R1.NBTTagList
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Zombie
import sharkbound.spigot.miscplugin.shared.builders.ItemBuilder
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.enums.allTags
import sharkbound.spigot.miscplugin.shared.enums.buildNBTCompound
import sharkbound.spigot.miscplugin.shared.enums.buildNBTTagList
import sharkbound.spigot.miscplugin.shared.extensions.*
import kotlin.contracts.ExperimentalContracts

// todo mob speed?
object CommandEverBlaze : BaseCommand("everblaze") {

    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isPlayer()) return false

        val attackDamage = args.getOrElse(1) { "2048" }.toDouble()
        val attackSpeed = args.getOrElse(0) { "2048" }.toDouble()

        buildItem(Material.GOLDEN_SWORD) {
            name = "&4Ever Blaze"

            genericAttackDamage(attackDamage, ItemBuilder.Slot.MainHand)
            genericAttackSpeed(attackSpeed, ItemBuilder.Slot.MainHand)
            enchant(Enchantment.FIRE_ASPECT, 1000)

            hideEnchants()
            hideAttributes()
            hideUnbreakable()

            unbreakable()

            lore(
                "",
                "${attackDamage.toInt()} Damage", "${attackSpeed.toInt()} Attack Speed",
                "",
                "&cSome say this sword embodies the hot flame of a dragon"
            )

        }.let { item ->
            sender.inventory.setItem(sender.inventory.heldItemSlot, item)
        }

        sender.world.spawnAs<Zombie>(sender.location).modifyNBT {
            allTags {
                getOrSet(attributes) { NBTTagList() }.apply {
                    add(buildNBTCompound {
                        setString(name, genericMaxHealth)
                        setInt(base, 100)
                    })
                }
            }
        }.apply {
            health = maxHealth
        }

        return false
    }
}