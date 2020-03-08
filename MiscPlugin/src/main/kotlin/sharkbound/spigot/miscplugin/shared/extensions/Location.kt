package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Location
import org.bukkit.util.Vector
import sharkbound.spigot.miscplugin.shared.utils.vector

infix fun Location.dist(end: Location): Double =
    dist(x, end.x, y, end.y, z, end.z)

infix fun Location.dist(end: Vector): Double =
    dist(x, end.x, y, end.y, z, end.z)

fun Location.locationsInRadius(radius: Int) =
    sequence {
        val range = -radius..radius
        yield(clone())
        for (x in range) {
            for (y in range) {
                for (z in range) {
                    yield(clone().add(vector(x, y, z)))
                }
            }
        }
    }

fun Location.blocksInRadius(radius: Int) =
    world?.blocksInRadius(this, radius) ?: emptySequence()

inline infix fun Location.clone(block: Location.() -> Unit) =
    clone().apply(block)

val Location.vector: Vector
    get() = toVector()