package sharkbound.spigot.miscplugin.shared.extensions

import net.minecraft.server.v1_14_R1.NBTBase
import net.minecraft.server.v1_14_R1.NBTTagCompound
import net.minecraft.server.v1_14_R1.NBTTagList
import sharkbound.spigot.miscplugin.shared.enums.NBTTags

fun NBTTagCompound.getList(key: String): NBTTagList? =
    get(key) as? NBTTagList

inline fun <reified T : NBTBase> NBTTagCompound.getOrSet(key: String, default: () -> T) =
    (get(key) as? T ?: default()).also { set(key, it) }

val NBTTagList.compounds
    get() = filterIsInstance<NBTTagCompound>()

fun NBTTagList.findByName(name: String): NBTTagCompound? =
    compounds.firstOrNull { it.getString(NBTTags.name) == name }
