package sharkbound.spigot.miscplugin.shared.enums

import net.minecraft.server.v1_14_R1.NBTTagCompound
import net.minecraft.server.v1_14_R1.NBTTagList

object NBTTags {
    const val attributes = "Attributes"
    const val invulnerable = "Invulnerable"
    const val id = "id"
    const val noAI = "NoAI"
    const val handItems = "HandItems"
    const val health = "Health"
    const val canPickUpLoot = "CanPickUpLoot"
    const val genericMovementSpeed = "generic.movementSpeed"
    const val genericArmor = "generic.armor"
    const val genericAttackDamage = "generic.attackDamage"
    const val genericAttackSpeed = "generic.attackSpeed"
    const val genericAttackKnockback = "generic.attackKnockback"
    const val genericMaxHealth = "generic.maxHealth"
    const val genericKnockbackResistance = "generic.knockbackResistance"
    const val ticksLived = "Spigot.ticksLived"
    const val armorItems = "ArmorItems"
    const val base = "Base"
    const val name = "Name"
    const val attributeModifiers = "AttributeModifiers"
    const val slot = "Slot"
    const val uuidMost = "UUIDMost"
    const val uuidLeast = "UUIDLeast"
    const val operation = "Operation"
    const val amount = "Amount"
    const val attributeName = "AttributeName"
    const val mainHand = "mainhand"
    const val offHand = "offhand"
    const val magicUUIDLeast = 894654
    const val magicUUIDMost = 2872
    const val unbreakable = "Unbreakable"
}

inline fun withTags(block: NBTTags.() -> Unit) {
    NBTTags.apply(block)
}

inline fun buildNBTCompound(block: NBTTagCompound.() -> Unit) =
    NBTTagCompound().apply(block)

inline fun buildNBTTagList(block: NBTTagList.() -> Unit) =
    NBTTagList().apply(block)