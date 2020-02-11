package sharkbound.spigot.skyblock.plugin.data

import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.*

open class LocationTrackerBase(configFile: String) : YamlBase(configFile) {
    fun remove(uuid: UUID) {
        val id = uuid.toString()
        if (id in config) {
            config[id] = null
            save()
        }
    }

    fun update(uuid: UUID, location: Location) {
        config.set(uuid.toString(), location)
        save()
    }

    open fun lastLocationOrDefault(player: Player): Location =
        throw NotImplementedError("base lastLocationOrDefault was not overridden")

    open fun lastLocationOrNull(player: Player): Location? =
        null
}