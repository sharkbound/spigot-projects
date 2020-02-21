package sharkbound.spigot.miscplugin.shared.builders

import net.minecraft.server.v1_14_R1.NBTTagCompound
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.spigot.miscplugin.shared.extensions.format
import sharkbound.spigot.miscplugin.shared.extensions.meta
import sharkbound.spigot.miscplugin.shared.extensions.nbt
import sharkbound.spigot.miscplugin.shared.extensions.nms
import net.minecraft.server.v1_14_R1.ItemStack as ServerStack

@DslMarker
annotation class ItemBuilderDSL

@ItemBuilderDSL
class ItemBuilder(private val material: Material) {
    var name: String? = null
    var amount: Int = 1
    var durability: Int? = null

    var item: ItemStack = ItemStack(material, amount)

    fun lore(vararg lines: String) {
        meta { lore = lines.map { it.format() } }
    }

    inline fun nms(block: ServerStack.() -> Unit) {
        item = item.nms(block)
    }

    inline fun nbt(block: NBTTagCompound.() -> Unit) {
        item = item.nbt(block)
    }

    inline fun meta(block: ItemMeta.() -> Unit) {
        item = item.meta(block)
    }

    fun enchant(enchantment: Enchantment, level: Int) {
        item.addUnsafeEnchantment(enchantment, level)
    }

    inline fun <reified T : ItemMeta> metaAs(block: T.() -> Unit) {
        item.itemMeta?.also { meta ->
            (meta as? T)?.let { castedMeta ->
                item.itemMeta = castedMeta.apply(block)
            }
        }
    }

    fun build(): ItemStack {
        durability?.let { meta { durability = it } }
        name?.let { meta { setDisplayName(it.format()) } }
        item.amount = amount
        return item
    }
}

@ItemBuilderDSL
inline fun buildItem(material: Material, block: ItemBuilder.() -> Unit) =
    ItemBuilder(material).apply(block).build()