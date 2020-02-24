package sharkbound.spigot.miscplugin.shared.builders

import net.minecraft.server.v1_14_R1.AttributeModifier
import net.minecraft.server.v1_14_R1.NBTTagCompound
import net.minecraft.server.v1_14_R1.NBTTagList
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.spigot.miscplugin.shared.enums.buildNBTCompound
import sharkbound.spigot.miscplugin.shared.enums.buildNBTTagList
import sharkbound.spigot.miscplugin.shared.enums.withTags
import sharkbound.spigot.miscplugin.shared.extensions.*
import net.minecraft.server.v1_14_R1.AttributeModifier.Operation as Operation
import net.minecraft.server.v1_14_R1.ItemStack as ServerStack

@DslMarker
annotation class ItemBuilderDSL

@ItemBuilderDSL
class ItemBuilder(private val material: Material) {
    enum class Slot(val value: String) {
        Head("head"),
        Body("body"),
        Legs("legs"),
        Feet("feet"),
        MainHand("mainhand"),
        OffHand("offhand"),
        All("all")
    }

    var name: String? = null
    var amount: Int = 1
    var durability: Int? = null
    var item: ItemStack = ItemStack(material, amount)

    fun genericAttackDamage(value: Double, targetSlot: Slot, mode: Operation = Operation.ADDITION) {
        withTags {
            nbt {
                set(attributeModifiers, (getList(attributeModifiers) ?: NBTTagList()).apply {
                    add(buildNBTCompound {
                        setString(attributeName, genericAttackDamage)
                        setString(name, genericAttackDamage)
                        setDouble(amount, value)
                        setInt(operation, mode.a())
                        setInt(uuidLeast, magicUUIDLeast)
                        setInt(uuidMost, magicUUIDMost)
                        setString(slot, targetSlot.value)
                    })
                })
            }
        }
    }

    fun genericAttackSpeed(value: Double, targetSlot: Slot) {
        withTags {
            nbt {
                set(attributeModifiers, (getList(attributeModifiers) ?: NBTTagList()).apply {
                    add(buildNBTCompound {
                        setString(attributeName, genericAttackSpeed)
                        setString(name, genericAttackSpeed)
                        setDouble(amount, value)
                        setInt(uuidLeast, magicUUIDLeast)
                        setInt(uuidMost, magicUUIDMost)
                        setString(slot, targetSlot.value)
                    })
                })
            }
        }
    }

    fun lore(vararg lines: String) {
        meta { lore = lines.map { it.color() } }
    }

    inline fun nms(block: ServerStack.() -> Unit) {
        item = item.nms(block)
    }

    inline fun nbt(block: NBTTagCompound.() -> Unit) {
        item = item.modifyNBT(block)
    }

    inline fun meta(block: ItemMeta.() -> Unit) {
        item = item.meta(block)
    }

    fun enchant(enchantment: Enchantment, level: Int) {
        item.addUnsafeEnchantment(enchantment, level)
    }

    fun flags(vararg flags: ItemFlag) {
        item.meta { addItemFlags(*flags) }
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
        name?.let { meta { setDisplayName(it.color()) } }
        item.amount = amount
        return item
    }
}

@ItemBuilderDSL
inline fun buildItem(material: Material, block: ItemBuilder.() -> Unit) =
    ItemBuilder(material).apply(block).build()