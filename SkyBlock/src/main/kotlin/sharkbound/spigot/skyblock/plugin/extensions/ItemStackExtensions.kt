package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

fun ItemStack.enchant(enchantment: Enchantment, level: Int = 1, ignoreMaxLevel: Boolean = true) =
    apply {
        itemMeta = itemMeta.apply { addEnchant(enchantment, level, ignoreMaxLevel) }
    }

infix fun ItemStack.hasEnchantment(enchantment: Enchantment) =
    enchantment in enchantments

fun ItemStack.displayName(newName: String) =
    apply {
        itemMeta = itemMeta.apply { displayName = newName }
    }

fun ItemStack.durability(newDura: Short) =
    apply {
        itemMeta = itemMeta.apply { durability = newDura }
    }

fun ItemStack.modifyMeta(func: ItemMeta.() -> Unit) =
    apply {
        itemMeta = itemMeta.apply(func)
    }
