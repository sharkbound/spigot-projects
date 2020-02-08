package sharkbound.spigot.skyblock.plugin.builders

import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.Material
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemFlags
import sharkbound.spigot.skyblock.plugin.utils.newStack
import net.minecraft.server.v1_8_R3.ItemStack as ServerItemStack

@DslMarker
annotation class ItemBuildDSL

@ItemBuildDSL
class ItemBuilder {
    var item = newStack(Material.AIR)

    fun copy() =
        item.clone()

    fun material(material: Material) {
        item = copy().apply { type = material }
    }

    fun amount(newAmount: Int) {
        item = copy().apply { amount = newAmount }
    }

    inline fun nms(block: ServerItemStack.() -> Unit) {
        item = copy().copyWithNMS(block)
    }

    inline fun nbt(block: NBTTagCompound.() -> Unit) {
        item = copy().copyWithNBT(block)
    }

    fun specialItemFlag(flag: SpecialItemFlags) {
        item = copy().applySpecialFlag(flag)
    }

    fun displayName(newName: String) {
        item = copy().displayName(newName)
    }

    fun lore(vararg lines: String) {
        item = copy().modifyMeta { lore = lines.map { it.colored() } }
    }

    fun lore(lines: List<String>) {
        item = copy().modifyMeta { lore = lines.map { it.colored() } }
    }

    fun durability(newDura: Short) {
        item = copy().durability(newDura)
    }
}

fun buildItem(block: ItemBuilder.() -> Unit) =
    ItemBuilder().apply(block).item