package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.skyblock.*

class TntListener : Listener {
    init {
//        register()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        val number = randInt(1, 3)
        (1..number).forEach { _ ->
            e.player.world.spawnEntityCast<TNTPrimed>(e.player.lookLocation, EntityType.PRIMED_TNT)?.fuseTicks = 0
        }

        e.player.sendColored("&l&4the stick of death&r has granted you &a&o$number&r &l&4TnT")
    }
}