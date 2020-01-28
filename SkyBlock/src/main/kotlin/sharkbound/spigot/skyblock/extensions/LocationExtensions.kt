package sharkbound.spigot.skyblock.extensions

import org.bukkit.Location

fun Location.add(x: Int, y: Int, z: Int) =
    add(x.toDouble(), y.toDouble(), z.toDouble())


enum class LocationAddMode { X, Y, Z }

fun Location.add(value: Int, mode: LocationAddMode) =
    when (mode) {
        LocationAddMode.X -> add(value, 0, 0)
        LocationAddMode.Y -> add(0, value, 0)
        LocationAddMode.Z -> add(0, 0, value)
    }


fun Location.add(value: Double, mode: LocationAddMode) =
    when (mode) {
        LocationAddMode.X -> add(value, 0.0, 0.0)
        LocationAddMode.Y -> add(0.0, value, 0.0)
        LocationAddMode.Z -> add(0.0, 0.0, value)
    }