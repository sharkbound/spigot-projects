package sharkbound.spigot.miscplugin.extensions

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.Location
import org.bukkit.entity.Player

fun ParticleEffect.spawn(
    center: Location,
    amount: Int = 1,
    speed: Double = 0.0,
    offsetX: Double = 0.0,
    offsetY: Double = 0.0,
    offsetZ: Double = 0.0,
    isLongRange: Boolean = false,
    owner: Player? = null
) {
    display(offsetX, offsetY, offsetZ, speed, amount, center, isLongRange, owner)
}