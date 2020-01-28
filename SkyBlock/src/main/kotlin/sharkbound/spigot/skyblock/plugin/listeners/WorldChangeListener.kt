package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import sharkbound.spigot.skyblock.extensions.*
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
        e.player.world.setBlock(e.player.location.add(-.5, LocationAddMode.Y), Material.GLASS)
        skyIslandGenerationQueue.remove(e.player.uniqueId)
    }
}