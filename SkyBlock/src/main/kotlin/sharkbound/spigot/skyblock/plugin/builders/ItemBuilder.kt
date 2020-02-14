package sharkbound.spigot.skyblock.plugin.builders

import net.minecraft.server.v1_8_R3.NBTTagCompound
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.utils.newItemStack
import net.minecraft.server.v1_8_R3.ItemStack as ServerItemStack

@DslMarker
annotation class ItemBuildDSL

@ItemBuildDSL
class ItemBuilder(initialItem: ItemStack? = null) {
    var item = initialItem?.clone() ?: newItemStack(Material.BEDROCK)

    fun copy() =
        item/*.clone()*/

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

    fun meta(block: ItemMeta.() -> Unit) {
        item = copy().modifyMeta(block)
    }

    fun specialItemFlag(flag: CustomItemFlag) {
        item = copy().applySpecialFlag(flag)
    }

    fun displayName(newName: String) {
        item = copy().displayName(newName.colored())
    }

    fun metaFlags(vararg flags: ItemFlag) {
        item = copy().modifyMeta { addItemFlags(*flags) }
    }

    inline fun <reified T : ItemMeta> metaCast(block: T.() -> Unit) {
        item = copy().modifyMetaCast(block)
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

//@ItemBuildDSL
fun buildItem(block: ItemBuilder.() -> Unit) =
    ItemBuilder().apply(block).item