package sharkbound.spigot.skyblock.plugin.extensions

import sharkbound.spigot.skyblock.plugin.utils.vect

val Int.x get() = vect(x = this)
val Int.y get() = vect(y = this)
val Int.z get() = vect(z = this)


val Double.x get() = vect(x = this)
val Double.y get() = vect(y = this)
val Double.z get() = vect(z = this)