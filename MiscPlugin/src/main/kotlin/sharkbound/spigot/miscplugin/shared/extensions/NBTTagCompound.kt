package sharkbound.spigot.miscplugin.shared.extensions

import net.minecraft.server.v1_14_R1.NBTList
import net.minecraft.server.v1_14_R1.NBTTagCompound
import net.minecraft.server.v1_14_R1.NBTTagList
import sharkbound.spigot.miscplugin.shared.enums.NBTTags

fun NBTTagCompound.getList(key: String): NBTTagList? =
    get(key) as? NBTTagList

val NBTTagList.compounds
    get() = filterIsInstance<NBTTagCompound>()

fun NBTTagList.findByName(name: String): NBTTagCompound? =
    compounds.firstOrNull { it.getString(NBTTags.name) == name }
