package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.Location
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.data.LocationTrackerBase
import sharkbound.spigot.skyblock.plugin.extensions.strId

object SkyIslandLocation : LocationTrackerBase("sky_island_locs.yml") {
    override fun lastLocationOrDefault(player: Player): Location =
        config[player.strId] as Location? ?: Locations.skyIslandSpawn(player)
}

object PreSkyIslandLocation : LocationTrackerBase("pre_sky_island_locs.yml") {
    override fun lastLocationOrDefault(player: Player): Location =
        config[player.strId] as Location
}