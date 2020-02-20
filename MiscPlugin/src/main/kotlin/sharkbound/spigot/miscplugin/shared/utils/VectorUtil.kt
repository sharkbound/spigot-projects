package sharkbound.spigot.miscplugin.shared.utils

import org.bukkit.util.Vector

fun vector(x: Int = 0, y: Int = 0, z: Int = 0): Vector =
    Vector(x, y, z)

fun vector(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0): Vector =
    Vector(x, y, z)

fun vector(x: Float = 0f, y: Float = 0f, z: Float = 0f): Vector =
    Vector(x, y, z)