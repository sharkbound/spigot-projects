package sharkbound.spigot.skyblock.extensions

import org.bukkit.Material

@Suppress("DEPRECATION")
val Material.idByte
    get() = id.toByte()