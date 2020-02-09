package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.Location
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.pitch
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorld
import sharkbound.spigot.skyblock.plugin.extensions.yaw

object Locations {
    const val SKY_ISLAND_SCHEMATIC_Y = 60.0
    val skyIslandSpawnOffset
        get() =
            org.bukkit.util.Vector(13, 21, 25)

    fun skyIslandSpawn(player: Player): Location =
        Location(
            player.skyBlockWorld,
            0.0,
            SKY_ISLAND_SCHEMATIC_Y,
            0.0,
            player.yaw,
            player.pitch
        ).add(skyIslandSpawnOffset)
}