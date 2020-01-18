package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.skyblock.colorFormat
import sharkbound.spigot.skyblock.register

class JoinListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        if (e.item?.type != null)
            return

        val number = randInt(1, 10)
        (1..number).forEach {
            (e.player.world.spawnEntity(
                e.player.getTargetBlock(setOf(Material.AIR), 1000).location,
                EntityType.PRIMED_TNT
            ) as TNTPrimed).fuseTicks = 20 * 5
        }

        e.player.sendMessage(
            colorFormat(
                "&l&4&oTHE HAND&r has granted you &a&o$number&r &l&4TnT"
            )
        )
    }
}