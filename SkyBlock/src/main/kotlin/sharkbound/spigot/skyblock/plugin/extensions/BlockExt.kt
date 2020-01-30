package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Material

@Suppress("DEPRECATION")
val Material.idByte
    get() = id.toByte()