package sharkbound.spigot.miscplugin.shared.utils

enum class TickUnit(val multiplayer: Int) {
    NONE(1), SECONDS(20), MINUTES(20 * 60), HOURS(20 * 60 * 60)
}

fun ticks(value: Long, unit: TickUnit): Long =
    value * unit.multiplayer

fun ticks(value: Double, unit: TickUnit): Long =
    (value * unit.multiplayer).toLong()

fun ticks(value: Float, unit: TickUnit): Long =
    (value * unit.multiplayer).toLong()

fun ticksInt(value: Long, unit: TickUnit): Int =
    (value * unit.multiplayer).toInt()

fun ticksInt(value: Double, unit: TickUnit): Int =
    (value * unit.multiplayer).toInt()

fun ticksInt(value: Float, unit: TickUnit): Int =
    (value * unit.multiplayer).toInt()

