package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.Location
import org.bukkit.entity.Player
import sharkbound.commonutils.collections.nonNullableMapOf
import sharkbound.commonutils.collections.nonNullableMutableMapOf
import sharkbound.spigot.skyblock.plugin.extensions.id
import java.util.*

object LocationHistory {
    private val skyBlockHistory = nonNullableMutableMapOf<UUID, Location>()

    fun update(from: Location, uuid: UUID) {
        skyBlockHistory[uuid] = from
    }

    fun remove(uuid: UUID) {
        if (contains(uuid)) {
            skyBlockHistory.remove(uuid)
        }
    }

    fun teleportBack(player: Player): Boolean {
        if (contains(player.id)) {
            player.teleport(get(player.id))
            remove(player.id)
            return true
        }
        return false
    }

    operator fun contains(o: Any) =
        o is UUID && o in skyBlockHistory

    operator fun get(key: UUID) =
        skyBlockHistory[key]
}