package sharkbound.spigot.miscplugin.shared.extensions

import net.minecraft.server.v1_14_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
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