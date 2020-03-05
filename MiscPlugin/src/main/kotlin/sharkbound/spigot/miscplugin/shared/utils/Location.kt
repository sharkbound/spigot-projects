package sharkbound.spigot.miscplugin.shared.utils

import org.bukkit.Location

fun locationsInArea(start: Location, end: Location): Sequence<Location> =
    sequence {
        val world = start.world
        for (x in start.x.toInt()..end.x.toInt()) {
            for (y in start.y.toInt()..end.y.toInt()) {
                for (z in start.z.toInt()..end.z.toInt()) {
                    yield(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
                }
            }
        }
    }