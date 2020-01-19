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
import sharkbound.spigot.skyblock.colorFormat
import sharkbound.spigot.skyblock.lookLocation
import sharkbound.spigot.skyblock.register
import sharkbound.spigot.skyblock.target

class JoinListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        if (e.item?.type != Material.STICK)
            return

//        val looking = e.player.lookLocation
//        val range = 20.0
//        e.player.world.getNearbyEntities(looking, range, range, range).filter { it !is Player }.forEach {
//            //            println(it.name)
//            it.velocity = it.velocity.add(looking.subtract(it.location).toVector())
//        }

        /*val number = randInt(1, 2)
        (1..number).forEach {
            (e.player.world.spawnEntity(
                e.player.target().location,
                EntityType.PRIMED_TNT
            ) as TNTPrimed).fuseTicks = 0
        }

        e.player.sendMessage(
            colorFormat(
                "&l&4the stick of death&r has granted you &a&o$number&r &l&4TnT"
            )
        )*/
    }
}