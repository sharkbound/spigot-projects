package sharkbound.spigot.miscplugin.listeners

import net.minecraft.server.v1_14_R1.ParticleType
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents
import sharkbound.spigot.miscplugin.shared.extensions.target
import sharkbound.spigot.miscplugin.shared.extensions.targetBlock

object PlayerEffectSpawner : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        e.player.apply {
            if (true) {
                spawnParticle(Particle.HEART, target(), 1, 0.0, 3.0, 0.0)
            }
        }
    }
}