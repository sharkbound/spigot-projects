package sharkbound.spigot.miscplugin.utils

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.Location
import org.bukkit.entity.Player

fun showParticle(
    particle: ParticleEffect,
    center: Location,
    amount: Int = 1,
    speed: Double = 0.0,
    offsetX: Double = 0.0,
    offsetY: Double = 0.0,
    offsetZ: Double = 0.0,
    isLongRange: Boolean = false,
    owner: Player? = null
) {
    particle.display(offsetX, offsetY, offsetZ, speed, amount, center, isLongRange, owner)
}