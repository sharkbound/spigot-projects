package sharkbound.spigot.miscplugin.listeners

import org.bukkit.Location
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.miscplugin.items.ArrowWand
import sharkbound.spigot.miscplugin.items.PhantomPortal
import sharkbound.spigot.miscplugin.items.ShulkerPortal
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.vector

data class ArrowSpawnInfo(val spawn: Location, val velocity: Vector, val speed: Float, val spread: Float)

object PlayerListener : Listener {

    init {
        registerEvents()
    }

    private val leftClicks = setOf(Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK)

    @EventHandler
    fun arrowHit(e: ProjectileHitEvent) {
        if (e.entityType == EntityType.ARROW) {
            e.entity.remove()
        }
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        when (e.item?.nbt?.getString("type")) {
            ShulkerWand.nbtId -> shulkerDeathBeam(e, 100.0)
            ShulkerPortal.nbtId -> shulkerPortal(e)
            PhantomPortal.nbtId -> phantomPortal(e)
            ArrowWand.nbtId -> arrowWand(e)
        }
    }

    private fun arrowWand(e: PlayerInteractEvent) {
        fun getArrowInfo(origin: Location, target: Location, direction: Vector): ArrowSpawnInfo {
            return ArrowSpawnInfo(origin, direction.multiply(5), 10f, 5f)
        }

        e.player.apply {
            nearestMob()?.let { mob ->
                targeted(mob)
                getArrowInfo(
                    location,
                    mob.location,
                    mob.location.subtract(location).toVector()
                ).apply {
                    world.spawnArrow(spawn, velocity, speed, spread)
                }
            }
        }
    }

    private val phantoms = mutableListOf<Phantom>()
    private val phantomPortals = mutableSetOf<Int>()

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

    private val shulkerPortals = mutableSetOf<Int>()
    private val SHULKER_INTERVAL: Long = 3
    val shulkerOffset get() = vector(x = randInt(-20, 20), y = randInt(-25, 25), z = randInt(-20, 20))

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
                shulkerPortals += cancellingRepeatingSyncTask(
                    0,
                    SHULKER_INTERVAL,
                    shouldCancel = { mob.isDead || taskId !in shulkerPortals }) {
                    world.spawnAs<ShulkerBullet>(loc.clone().add(shulkerOffset)).target = mob
                }.taskId
            }
        }
    }

    @Suppress("SameParameterValue")
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
}

private fun Player.targeted(mob: LivingEntity) {
    send("&eTargeted ${mob.type.name} that is ${location.distance(mob.location).toInt()} blocks away")
}

private fun Player.nearestMob(requireLOS: Boolean = true): LivingEntity? {
    val range = 200.0
    return world.getNearbyEntities(target(), range, range, range).asSequence().filter { it !is Player }
        .filterIsInstance<LivingEntity>().filter { !requireLOS || hasLineOfSight(it) }
        .minBy { it.location.distance(target()) }
}
