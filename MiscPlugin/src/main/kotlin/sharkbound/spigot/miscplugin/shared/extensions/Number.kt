package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.util.Vector
import sharkbound.spigot.miscplugin.shared.utils.vector

val Int.ticks
    get() = toLong()

val Double.secondTicks
    get() = (this / 20).toLong()

val Int.secondTicks
    get() = toDouble().secondTicks

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