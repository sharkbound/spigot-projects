package sharkbound.spigot.miscplugin.listeners

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.Material
import org.bukkit.entity.Wolf
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.utils.showParticle
import kotlin.contracts.ExperimentalContracts

object TestingListener : BaseListener() {
    @ExperimentalContracts
    @EventHandler
    fun playerInteract(e: PlayerInteractEvent) {
    }

    var updates = 0

    @EventHandler
    fun playerMove(e: PlayerMoveEvent) {
        updates += 1
        e.player.apply {
            val pos = eyeLocation
            val dir = direction
            val start = pos.clone { add(direction.clone { multiply(1) }) }
            val end = pos.clone { add(direction.clone { multiply(10) }) }

            val steps = 10
            val interval = start.dist(end) / steps

            (1..steps).forEach {

            }

            showParticle(ParticleEffect.FLAME, end)
        }
    }
}