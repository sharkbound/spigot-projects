package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorldName
import sharkbound.spigot.skyblock.plugin.skyIslandGenerationQueue

class WorldChangeListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onWorldChanged(e: PlayerChangedWorldEvent) {
        val id = e.player.uniqueId

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