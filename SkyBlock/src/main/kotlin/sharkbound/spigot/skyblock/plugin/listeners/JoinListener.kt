package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.TNTPrimed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.commonutils.util.randInt
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
        val number = randInt(1, 10)
        (1..number).forEach {
            if (e.item?.type == Material.STICK) {
                (e.player.world.spawnEntity(
                    e.player.getTargetBlock(setOf(Material.AIR), 1000).location,
                    EntityType.PRIMED_TNT
                ) as TNTPrimed).fuseTicks = 20 * 5
            }
        }
        e.player.sendMessage(
            "${ChatColor.BOLD}${ChatColor.LIGHT_PURPLE}the stick of death has graced you with " +
                    "$number " +
                    "${ChatColor.YELLOW}${ChatColor.MAGIC}A${ChatColor.RESET}${ChatColor.RED}TNT${ChatColor.YELLOW}${ChatColor.MAGIC}A"
        )
    }
}