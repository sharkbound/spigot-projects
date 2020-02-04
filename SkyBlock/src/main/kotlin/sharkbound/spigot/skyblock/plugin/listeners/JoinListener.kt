package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.spigot.skyblock.plugin.DB
import sharkbound.spigot.skyblock.plugin.extensions.register
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorldName
import sharkbound.spigot.skyblock.plugin.skyIslandGenerationQueue

class JoinListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onWorldChanged(e: PlayerJoinEvent) {
        DB.initPlayer(e.player)
        println(DB.playerData(e.player))
    }
}