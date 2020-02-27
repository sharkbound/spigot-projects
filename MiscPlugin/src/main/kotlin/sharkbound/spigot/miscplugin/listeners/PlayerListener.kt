package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector
import sharkbound.commonutils.util.randDouble
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.miscplugin.items.ArrowWand
import sharkbound.spigot.miscplugin.items.PhantomPortal
import sharkbound.spigot.miscplugin.items.ShulkerPortal
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.delaySyncTask
import sharkbound.spigot.miscplugin.shared.utils.vector

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
        fun rand(range: Int): Vector {
            fun r() =
                randDouble(-range.toDouble(), range.toDouble())
            return vector(r(), r(), r())
        }

        val arrowRange = 100
        val arrowSpeed = 30f
        val arrowAccuracy = 0f

        e.player.apply {
            nearestMob()?.let { mob ->
                targeted(mob)
                val arrowSpawn = mob.location.add(rand(arrowRange))
                val arrowAngle = mob.location.subtract(arrowSpawn).toVector()
                world.spawnArrow(arrowSpawn, arrowAngle, arrowSpeed, arrowAccuracy)
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

private fun Player.nearestMob(): LivingEntity? {
    val range = 200.0
    return world.getNearbyEntities(target(), range, range, range).asSequence().filter { it !is Player }
        .filterIsInstance<LivingEntity>().minBy { it.location.distance(target()) }
}