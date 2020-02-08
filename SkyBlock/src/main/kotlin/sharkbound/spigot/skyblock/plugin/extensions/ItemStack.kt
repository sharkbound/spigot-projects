package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.commonutils.extensions.isTrue

fun ItemStack.enchant(enchantment: Enchantment, level: Int = 1, ignoreMaxLevel: Boolean = true) =
    modifyMeta {
        addEnchant(enchantment, level, ignoreMaxLevel)
    }

infix fun ItemStack.hasEnchantment(enchantment: Enchantment) =
    enchantment in enchantments

fun ItemStack.displayName(newName: String) =
    modifyMeta { displayName = newName }

fun ItemStack.durability(newDura: Short) =
    modifyMeta { durability = newDura }

fun ItemStack.modifyMeta(func: ItemMeta.() -> Unit) =
    apply {
        itemMeta = itemMeta.apply(func)
    }

fun ItemStack.withMeta(func: ItemMeta.() -> Unit) =
    itemMeta.apply(func)

infix fun ItemStack?.displayNameIs(other: String): Boolean =
    this?.itemMeta?.displayName?.equals(other, ignoreCase = true).isTrue

val ItemStack.name
    get() = itemMeta?.displayName ?: type.name
