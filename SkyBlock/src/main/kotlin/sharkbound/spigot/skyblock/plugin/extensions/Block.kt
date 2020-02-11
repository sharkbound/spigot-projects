package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Material
import org.bukkit.block.BlockFace

@Suppress("DEPRECATION")
val Material.idByte
    get() = id.toByte()

//val BlockFace.offset
//    get() =