package sharkbound.spigot.miscplugin.shared.builders

import net.minecraft.server.v1_14_R1.NBTTagCompound
import net.minecraft.server.v1_14_R1.NBTTagList
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import sharkbound.spigot.miscplugin.shared.enums.NBTTags
import sharkbound.spigot.miscplugin.shared.enums.buildNBTCompound
import sharkbound.spigot.miscplugin.shared.enums.allTags
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

    private inline fun setGenericAttribute(
        attrName: String,
        targetSlot: Slot,
        mode: Operation = Operation.ADDITION,
        block: NBTTagCompound.() -> Unit
    ) {
        nbt {
            allTags {
                getOrSet(attributeModifiers) { NBTTagList() }.apply {
                    add(buildNBTCompound {
                        setString(attributeName, attrName)
                        setString(name, attrName)
                        setInt(operation, mode.a())
                        setString(slot, targetSlot.value)
                        setInt(uuidLeast, magicUUIDLeast)
                        setInt(uuidMost, magicUUIDMost)
                        block()
                    })
                }
            }
        }
    }

    fun genericAttackDamage(value: Double, targetSlot: Slot, mode: Operation = Operation.ADDITION) {
        allTags {
            setGenericAttribute(genericAttackDamage, targetSlot, mode) {
                setDouble(amount, value)
            }
        }
    }

    fun genericAttackSpeed(value: Double, targetSlot: Slot, mode: Operation = Operation.ADDITION) {
        allTags {
            setGenericAttribute(genericAttackSpeed, targetSlot, mode) {
                setDouble(amount, value)
            }
        }
    }

    fun genericMaxHealth(value: Double, targetSlot: Slot, mode: Operation = Operation.ADDITION) {
        allTags {
            setGenericAttribute(genericMaxHealth, targetSlot, mode) {
                setDouble(amount, value)
            }
        }
    }

    fun lore(vararg lines: String) {
        meta { lore = lines.map { it.color() } }
    }

    fun lore(lines: List<String>) {
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

    fun unbreakable() {
        nbt {
            setByte(NBTTags.unbreakable, 1)
        }
    }

    fun hideAttributes() {
        flags(ItemFlag.HIDE_ATTRIBUTES)
    }

    fun hideUnbreakable() {
        flags(ItemFlag.HIDE_UNBREAKABLE)
    }

    fun hideEnchants() {
        flags(ItemFlag.HIDE_ENCHANTS)
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