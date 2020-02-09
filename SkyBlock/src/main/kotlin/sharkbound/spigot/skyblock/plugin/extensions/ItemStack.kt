package sharkbound.spigot.skyblock.plugin.extensions

import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.spigot.skyblock.plugin.objects.NbtTags
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemFlags
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
    this?.itemMeta?.displayName?.equals(other, ignoreCase = true) == true

val ItemStack.name
    get() = itemMeta?.displayName ?: type.name

val ItemStack.nms: ServerItemStack
    get() = CraftItemStack.asNMSCopy(this)

fun ItemStack.ensureNBT() =
    nms.ensureNBT().asBukkit


fun ServerItemStack.ensureNBT() =
    apply {
        tag = tag ?: NBTTagCompound()
    }


inline fun ItemStack.copyWithNBT(block: NBTTagCompound.() -> Unit): ItemStack =
    copyWithNMS { block(tag) }

inline fun ItemStack.copyWithNMS(block: ServerItemStack.() -> Unit): ItemStack =
    nms.ensureNBT().apply { block(this) }.asBukkit

fun ItemStack.hasTag(tag: String): Boolean =
    nms.tag?.hasKey(tag) == true

val ServerItemStack.asBukkit: ItemStack
    get() = CraftItemStack.asBukkitCopy(this)

infix fun ItemStack.applySpecialFlag(flag: SpecialItemFlags): ItemStack =
    copyWithNBT { setString(NbtTags.ITEM_CLASS, flag.nbtValue) }

infix fun ItemStack?.hasSpecialItemFlag(flag: SpecialItemFlags): Boolean =
    this?.nms?.tag?.getString(NbtTags.ITEM_CLASS) == flag.nbtValue

val ItemStack?.hasItemClass: Boolean
    get() = this?.hasTag(NbtTags.ITEM_CLASS) == true

val ItemStack.nbt: NBTTagCompound
    get() = nms.ensureNBT().tag

val ItemStack?.specialItemFlag: SpecialItemFlags?
    get() =
        this?.nbt?.getString(NbtTags.ITEM_CLASS)?.let { value ->
            SpecialItemFlags.values().firstOrNull { it.nbtValue == value }
        }
