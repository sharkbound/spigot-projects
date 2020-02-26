package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.miscplugin.items.ArrowPortal
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

    private val shulkerPortals = mutableSetOf<Int>()
    private val leftClicks = setOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)
    private val phantoms = mutableListOf<Phantom>()
    private val phantomPortals = mutableSetOf<Int>()
    private val arrowPortals = mutableSetOf<Int>()

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        when (e.item?.nbt?.getString("type")) {
            ShulkerWand.nbtId -> shulkerDeathBeam(e, 100.0)
            ShulkerPortal.nbtId -> shulkerPortal(e)
            PhantomPortal.nbtId -> phantomPortal(e)
            ArrowPortal.nbtId -> arrowPortal(e)
        }
    }

    private fun arrowPortal(e: PlayerInteractEvent) {
        fun reset() {
            arrowPortals.clear()
        }

        if (e.action in leftClicks) {
            reset()
            e.player.send("&eStopped all arrow portals")
            return
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
            nearestMob()?.let { mob ->
                targeted(mob)
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
            nearestMob()?.let { mob ->
                targeted(mob)
                shulkerPortals += cancellingRepeatingSyncTask(0, 5, { mob.isDead || taskId !in shulkerPortals }) {
                    world.spawnAs<ShulkerBullet>(loc).target = mob
                }.taskId
            }
        }
    }

    private fun Player.targeted(mob: LivingEntity) {
        send("&eTargeted ${mob.type.name} that is ${location.distance(mob.location).toInt()} blocks away")
    }

    private fun shulkerDeathBeam(e: PlayerInteractEvent, yOffset: Double) {
        e.player.apply {
            nearestMob()?.let { mob ->
                targeted(mob)
                repeat(30) {
                    world.spawnAs<ShulkerBullet>(mob.location.add(vector(y = yOffset))).target = mob
                }
            }
        }
    }

    private fun Player.nearestMob(): LivingEntity? {
        val range = 200.0
        return world.getNearbyEntities(target(), range, range, range).asSequence().filter { it !is Player }
            .filterIsInstance<LivingEntity>().minBy { it.location.distance(target()) }
    }
}