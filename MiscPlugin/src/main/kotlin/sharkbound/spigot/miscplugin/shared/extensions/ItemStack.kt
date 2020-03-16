package sharkbound.spigot.miscplugin.shared.extensions

import net.minecraft.server.v1_14_R1.NBTTagCompound
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract
import net.minecraft.server.v1_14_R1.ItemStack as ServerStack

inline infix fun ItemStack.modifyMeta(block: ItemMeta.() -> Unit): ItemStack =
    apply {
        itemMeta = itemMeta?.also(block)
    }

inline infix fun ItemStack.nms(block: ServerStack.() -> Unit): ItemStack =
    CraftItemStack.asBukkitCopy(nms.apply(block))

inline infix fun ItemStack.modifyNBT(block: NBTTagCompound.() -> Unit): ItemStack =
    nms { tag = orCreateTag.apply(block) }

val ItemStack.nms: ServerStack
    get() = CraftItemStack.asNMSCopy(this)

val ItemStack.nbt: NBTTagCompound
    get() = nms.orCreateTag

infix fun ItemStack?.hasNBTKey(key: String) =
    this?.nbt?.hasKey(key) ?: false

@ExperimentalContracts
infix fun ItemStack?.typeIs(material: Material): Boolean {
    contract {
        returns(true) implies (this@typeIs != null)
    }
    return this?.type == material
}