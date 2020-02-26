package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Phantom
import org.bukkit.entity.Player
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.miscplugin.items.PhantomPortal
import sharkbound.spigot.miscplugin.items.ShulkerPortal
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.vector

object PlayerListener : Listener {
    init {
        registerEvents()
    }

    val shulkerPortals = mutableSetOf<Int>()
    val leftClicks = setOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)
    val phantoms = mutableListOf<Phantom>()
    val phantomPortals = mutableSetOf<Int>()

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        when (e.item?.nbt?.getString("type")) {
            ShulkerWand.nbtId -> shulkerDeathBeam(e, 100.0)
            ShulkerPortal.nbtId -> shulkerPortal(e)
            PhantomPortal.nbtId -> phantomPortal(e)
        }
    }

    private fun phantomPortal(e: PlayerInteractEvent) {
        fun reset() {
            phantoms.apply { forEach { it.kill() }; clear() }
            phantomPortals.clear()
        }

        if (e.action in leftClicks) {
            reset()
            e.player.send("&aKilled all phantoms")
            return
        }

        e.player.apply {
            val look = target()
            val loc = location
            world.livingEntities.filter { it !is Player }.minBy { it.location.distance(look) }?.let { mob ->
                send("&eTargeted ${mob.type.name} that is ${location.distance(mob.location).toInt()} blocks away")
                phantomPortals += cancellingRepeatingSyncTask(
                    0,
                    10,
                    { mob.isDead || taskId !in phantomPortals },
                    onCancel = { reset() }
                ) {
                    phantoms += world.spawnAs<Phantom>(loc).apply { target = mob }
                }.taskId
            }
        }
    }

    private fun shulkerPortal(e: PlayerInteractEvent) {
        if (e.action in leftClicks) {
            shulkerPortals.clear()
            e.player.send("&acleared all shulker portals")
            e.player.world.entities.filterIsInstance<ShulkerBullet>().forEach { it.target = null }
            return
        }

        e.player.apply {
            val loc = location
            val look = target()
            world.livingEntities.filter { it !is Player }.minBy { look.distance(it.location) }?.let { mob ->
                send("&eTargeted ${mob.type.name} that is ${location.distance(mob.location).toInt()} blocks away")
                shulkerPortals += cancellingRepeatingSyncTask(0, 5, { mob.isDead || taskId !in shulkerPortals }) {
                    world.spawnAs<ShulkerBullet>(loc).target = mob
                }.taskId
            }
        }
    }

    private fun shulkerDeathBeam(e: PlayerInteractEvent, yOffset: Double) {
        e.player.apply {
            world.getNearbyEntities(target(), 20.0, 20.0, 20.0).asSequence()
                .filter { it !is Player && it is LivingEntity }.minBy { it.location.distance(target()) }
                ?.let { mob ->
                    repeat(30) {
                        world.spawnAs<ShulkerBullet>(mob.location.add(vector(y = yOffset))).target = mob
                    }
                }
        }
    }
}