package sharkbound.spigot.miscplugin.shared.extensions

import net.minecraft.server.v1_14_R1.NBTTagCompound
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity
import org.bukkit.entity.Entity
import net.minecraft.server.v1_14_R1.Entity as ServerEntity

val Entity.nms
    get() = this as CraftEntity

val Entity.handle: ServerEntity
    get() = nms.handle

inline fun ServerEntity.nbt(block: NBTTagCompound.() -> Unit): ServerEntity =
    apply {
        f(nbt.apply(block))
    }

val ServerEntity.nbt get() = NBTTagCompound().apply { c(this) }

fun ServerEntity.noAI(): ServerEntity =
    nbt { setInt("NoAI", 1) }

fun Entity.noAI(): Entity =
    handle.noAI().bukkitEntity
