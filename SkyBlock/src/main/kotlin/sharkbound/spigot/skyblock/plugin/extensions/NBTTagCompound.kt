package sharkbound.spigot.skyblock.plugin.extensions

import net.minecraft.server.v1_8_R3.NBTTagCompound

fun NBTTagCompound.setAllStrings(vararg pairs: Pair<String, String>) {
    for ((key, value) in pairs) {
        setString(key, value)
    }
}