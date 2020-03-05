package sharkbound.spigot.miscplugin.shared.extensions

import net.minecraft.server.v1_14_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import java.util.*
import net.minecraft.server.v1_14_R1.Entity as ServerEntity

val Entity.nms
    get() = this as CraftEntity

val Entity.handle: ServerEntity
    get() = nms.handle

inline fun ServerEntity.modifyNBT(block: NBTTagCompound.() -> Unit): ServerEntity =
    apply {
        NBTTagCompound().also {
            c(it)
            it.apply(block)
            f(it)
        }
    }

val ServerEntity.nbt
    get() = NBTTagCompound().apply { c(this) }

fun ServerEntity.noAI(): ServerEntity =
    modifyNBT { setInt("NoAI", 1) }

fun Entity.noAI(): Entity =
    handle.noAI().bukkitEntity

val Entity.nbt
    get() = handle.nbt

inline infix fun <T : Entity> T.modifyNBT(block: NBTTagCompound.() -> Unit): T =
    apply { handle.modifyNBT(block) }

fun LivingEntity.kill() {
    damage(health)
}

val Entity.id
    get() = uniqueId

val Entity.eid
    get() = entityId

// id match

infix fun Entity.idIs(other: UUID): Boolean =
    uniqueId == other

infix fun Entity.idIs(other: Entity): Boolean =
    uniqueId == other.uniqueId

infix fun Entity.entityIdIs(other: Int): Boolean =
    entityId == other

infix fun Entity.entityIdIs(other: Entity): Boolean =
    entityId == other.entityId

// inverted id match, aka, not matches

infix fun Entity.idIsNot(other: UUID): Boolean =
    uniqueId != other

infix fun Entity.idIsNot(other: Entity): Boolean =
    uniqueId != other.uniqueId

infix fun Entity.entityIdIsNot(other: Int): Boolean =
    entityId != other

infix fun Entity.entityIdIsNot(other: Entity): Boolean =
    entityId != other.entityId