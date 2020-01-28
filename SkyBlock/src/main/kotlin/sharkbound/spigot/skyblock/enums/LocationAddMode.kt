package sharkbound.spigot.skyblock.enums

import sharkbound.spigot.skyblock.utils.vect

enum class LocationAddMode {
    X, Y, Z;

    fun toVect(value: Int) =
        vect(value, this)

    fun toVect(value: Double) =
        vect(value, this)
}