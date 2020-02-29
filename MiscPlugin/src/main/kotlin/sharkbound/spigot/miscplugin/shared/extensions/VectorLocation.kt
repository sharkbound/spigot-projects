package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.absoluteValue

private fun dist(x1: Double, x2: Double, y1: Double, y2: Double, z1: Double, z2: Double): Double =
    (x1 - x2).absoluteValue + (y1 - y2).absoluteValue + (z1 - z2).absoluteValue

infix fun Vector.dist(end: Vector): Double =
    dist(x, end.x, y, end.y, z, end.z)

infix fun Vector.dist(end: Location): Double =
    dist(x, end.x, y, end.y, z, end.z)

infix fun Location.dist(end: Location): Double =
    dist(x, end.x, y, end.y, z, end.z)

infix fun Location.dist(end: Vector): Double =
    dist(x, end.x, y, end.y, z, end.z)
