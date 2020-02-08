package sharkbound.spigot.skyblock.plugin.extensions

import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.commonutils.extensions.isTrue
import net.minecraft.server.v1_8_R3.ItemStack as ServerItemStack

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

val ItemStack.nms: ServerItemStack
    get() = CraftItemStack.asNMSCopy(this)

inline fun <R> ItemStack.useNMS(block: ServerItemStack.() -> R) =
    nms.run(block)

fun ItemStack.ensureNBT() =
    nms.ensureNBT().asBukkit


fun ServerItemStack.ensureNBT() =
    apply {
        tag = tag ?: NBTTagCompound()
    }


inline fun <R> ItemStack.copyWithNBT(block: NBTTagCompound.() -> R): ItemStack =
    nms.ensureNBT().apply { block(tag) }.asBukkit

fun ItemStack.hasTag(tag: String): Boolean =
    nms.tag?.hasKey(tag).isTrue

val ServerItemStack.asBukkit get() = CraftItemStack.asBukkitCopy(this)