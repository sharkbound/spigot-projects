package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerTeleportEvent
import sharkbound.spigot.skyblock.plugin.extensions.id
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorldName
import sharkbound.spigot.skyblock.plugin.objects.LocationHistory
import sharkbound.spigot.skyblock.plugin.skyIslandGenerationQueue

class SkyBlockWorldChangeListener : Listener {
    init {
        register()
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onBeforeTeleport(e: PlayerTeleportEvent) {
        val from = e.from
        val to = e.to

        if (e.isCancelled) {
            return
        }

        // is the player teleporting to their sky island?
        if (from.world.name != e.player.skyBlockWorldName && to.world.name == e.player.skyBlockWorldName) {
            LocationHistory.update(e.from, e.player.id)
        }
    }

    @EventHandler
    fun onWorldChanged(e: PlayerChangedWorldEvent) {
        val id = e.player.id

        if (e.player.world.name != e.player.skyBlockWorldName) {
            return
        }

//      was the skyblock world just generated?
        if (id !in skyIslandGenerationQueue) {
            return
        }

        if (e.player.world.name == e.player.skyBlockWorldName) {
            skyIslandGenerationQueue[id].generate()
            skyIslandGenerationQueue.remove(id)
        }
    }
}