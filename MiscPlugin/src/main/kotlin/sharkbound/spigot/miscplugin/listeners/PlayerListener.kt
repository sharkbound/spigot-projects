package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.extensions.nbt
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents
import sharkbound.spigot.miscplugin.shared.extensions.spawnAs
import sharkbound.spigot.miscplugin.shared.extensions.target
import sharkbound.spigot.miscplugin.shared.utils.vector

object PlayerListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {

        if (e.item?.nbt?.getString("type") == ShulkerWand.nbtId) {
            e.player.apply {
                world.getNearbyEntities(target(), 20.0, 20.0, 20.0).asSequence()
                    .filter { it !is Player && it is LivingEntity }.minBy { it.location.distance(target()) }
                    ?.let { mob ->
                        val loc = mob.location.add(vector(y = 200))
                        world.spawnAs<ShulkerBullet>(loc).apply { target = mob; mob.isGlowing = true; isGlowing = true }
                    }
            }
        }
    }
}