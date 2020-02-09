package sharkbound.spigot.skyblock.plugin.database

import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.closeAfter
import sharkbound.spigot.skyblock.plugin.extensions.div
import sharkbound.spigot.skyblock.plugin.extensions.id
import sharkbound.spigot.skyblock.plugin.objects.FilePaths
import sharkbound.spigot.skyblock.plugin.objects.Locations
import java.io.FileWriter
import java.util.*

object SkyIslandLocation {
    private val config: YamlConfiguration
    private val path = FilePaths.configFolder / "sky_island_locs.yml"

    val exists
        get() = path.toFile().exists()

    init {
        if (!exists) {
            FileWriter(path.toString()).closeAfter {
                write("")
            }
        }

        config = YamlConfiguration.loadConfiguration(path.toFile())
    }

    private fun save() {
        config.save(path.toString())
    }

    fun remove(player: Player): Unit =
        remove(player.id)

    fun remove(uuid: UUID) {
        val id = uuid.toString()
        if (id in config) {
            config[id] = null
            save()
        }
    }

    fun update(player: Player, location: Location): Unit =
        update(player.id, location)

    fun update(uuid: UUID, location: Location) {
        config.set(uuid.toString(), location)
        save()
    }

    fun lastLocation(player: Player): Location =
        config[player.id.toString()] as Location? ?: Locations.skyIslandSpawn(player)
}
