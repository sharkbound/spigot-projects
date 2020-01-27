package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import sharkbound.spigot.skyblock.extensions.register
import sharkbound.spigot.skyblock.extensions.skyBlockWorldName
import sharkbound.spigot.skyblock.skyIslandGenerationQueue

class WorldChangeListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onWorldChanged(e: PlayerChangedWorldEvent) {
        if (e.player.uniqueId !in skyIslandGenerationQueue || e.player.world.name.toLowerCase() != e.player.skyBlockWorldName) {
            return
        }

        skyIslandGenerationQueue[e.player.uniqueId].generate()
        skyIslandGenerationQueue.remove(e.player.uniqueId)
    }
}