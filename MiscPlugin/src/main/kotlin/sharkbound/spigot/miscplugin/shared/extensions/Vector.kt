package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.absoluteValue

fun dist(x1: Double, x2: Double, y1: Double, y2: Double, z1: Double, z2: Double): Double =
    (x1 - x2).absoluteValue + (y1 - y2).absoluteValue + (z1 - z2).absoluteValue

infix fun Vector.dist(end: Vector): Double =
    dist(x, end.x, y, end.y, z, end.z)

infix fun Vector.dist(end: Location): Double =
    dist(x, end.x, y, end.y, z, end.z)

inline infix fun Vector.clone(block: Vector.() -> Unit): Vector =
    clone().apply(block)

operator fun Vector.minus(other: Vector): Vector =
    clone { subtract(other) }

operator fun Vector.plus(other: Vector): Vector =
    clone { add(other) }

operator fun Vector.div(other: Vector): Vector =
    clone { divide(other) }

operator fun Vector.times(other: Vector): Vector =
    clone { multiply(other) }

operator fun Vector.times(other: Double): Vector =
    clone { multiply(other) }

operator fun Vector.times(other: Float): Vector =
    clone { multiply(other) }

operator fun Vector.times(other: Int): Vector =
    clone { multiply(other) }
