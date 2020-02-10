package sharkbound.spigot.skyblock.plugin.data

import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.closeAfter
import sharkbound.spigot.skyblock.plugin.extensions.div
import sharkbound.spigot.skyblock.plugin.extensions.id
import sharkbound.spigot.skyblock.plugin.objects.FilePaths
import java.io.FileWriter
import java.util.*

open class LocationTrackerBase(configFile: String) {
    protected val path = FilePaths.configFolder / configFile
    protected val config: YamlConfiguration


    init {
        if (exists) {
            FileWriter(path.toString()).closeAfter {
                write("")
            }
        }
        config = YamlConfiguration.loadConfiguration(path.toFile())
    }

    val exists
        get() = path.toFile().exists()

    protected fun save() {
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

    open fun lastLocation(player: Player): Location =
        throw NotImplementedError("base lastLocation was not overridden")
}