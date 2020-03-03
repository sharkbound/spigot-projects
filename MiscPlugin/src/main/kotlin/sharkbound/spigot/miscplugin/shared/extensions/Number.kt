package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.util.Vector
import sharkbound.spigot.miscplugin.shared.utils.TickUnit
import sharkbound.spigot.miscplugin.shared.utils.ticks
import sharkbound.spigot.miscplugin.shared.utils.vector

val Int.x: Vector
    get() = vector(x = this)

val Int.y: Vector
    get() = vector(y = this)

val Int.z: Vector
    get() = vector(z = this)

val Double.x: Vector
    get() = vector(x = this)

val Double.y: Vector
    get() = vector(y = this)

val Double.z: Vector
    get() = vector(z = this)

fun Int.ticks(unit: TickUnit): Long =
    ticks(toLong(), unit)

fun Float.ticks(unit: TickUnit): Long =
    ticks(this, unit)

fun Double.ticks(unit: TickUnit): Long =
    ticks(this, unit)