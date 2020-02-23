package sharkbound.spigot.miscplugin.shared.enums

object NBTTags {
    const val attributes = "Attributes"
    const val invulnerable = "Invulnerable"
    const val id = "id"
    const val noAI = "NoAI"
    const val handItems = "HandItems"
    const val health = "Health"
    const val canPickUpLoot = "CanPickUpLoot"
    const val movementSpeed = "generic.movementSpeed"
    const val armor = "generic.armor"
    const val attackDamage = "generic.attackDamage"
    const val attackKnockback = "generic.attackKnockback"
    const val maxHealth = "generic.maxHealth"
    const val knockbackResistance = "generic.knockbackResistance"
    const val ticksLived = "Spigot.ticksLived"
    const val armorItems = "ArmorItems"
    const val base = "Base"
    const val name = "Name"
}

inline fun withTags(block: NBTTags.() -> Unit) {
    NBTTags.apply(block)
}