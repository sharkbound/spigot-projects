package sharkbound.spigot.miscplugin.listeners

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.utils.showParticle
import kotlin.contracts.ExperimentalContracts

object TestingListener : BaseListener() {
    private const val KILL_RAY_RANGE = 10

    @ExperimentalContracts
    @EventHandler
    fun playerInteract(e: PlayerInteractEvent) {
//        e player {
//            if (inventory.itemInMainHand typeIs Material.GOLDEN_SWORD) {
//                val start = eyeLocation
//                val end = start.clone { add(direction.multiply(KILL_RAY_RANGE)) }
//                particleLine(ParticleEffect.FLAME, start, end)
//
//                world.livingEntities.filter { it idIsNot id && nearLine(it.location, start, end) }
//                    .forEach(LivingEntity::kill)
//            }
//        }
    }

    @EventHandler
    fun playerMove(e: PlayerMoveEvent) {
    }

    @Suppress("SameParameterValue")
    private fun particleLine(particleEffect: ParticleEffect, start: Location, end: Location) {
        val direction = (end.vector - start.vector).normalize() * 1
        val pos = start.clone()

        showParticle(particleEffect, start, isLongRange = true)
        while (pos dist end > 1.2) {
            showParticle(particleEffect, pos)
            pos.add(direction)
        }
        showParticle(particleEffect, end, isLongRange = true)
    }

    private fun nearLine(point: Location, start: Location, end: Location): Boolean {
        val endToStart = end dist start
        if (point dist end > endToStart || point dist start > endToStart) {
            return false
        }
        return true
    }
}