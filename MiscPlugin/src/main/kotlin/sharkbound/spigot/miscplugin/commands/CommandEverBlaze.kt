package sharkbound.spigot.miscplugin.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import sharkbound.spigot.miscplugin.shared.builders.ItemBuilder
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.commands.BaseCommand
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

        }.let {
            sender replaceHeldItem it
        }

        return false
    }
}