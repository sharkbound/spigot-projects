package sharkbound.spigot.miscplugin.wands

import dev.esophose.playerparticles.particles.ParticleEffect
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.commonutils.util.randDouble
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import sharkbound.spigot.miscplugin.shared.utils.*
import sharkbound.spigot.miscplugin.utils.WandUtil
import sharkbound.spigot.miscplugin.utils.showParticle
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object FireWand : Wand {
    override val nbtId = "firewand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&4Fire Wand"
            setWandData()
        }
}

object FireWandListener : BaseListener() {
    private const val FIRE_CHANCE = .01
    const val burstDamage = 10.0
    const val burstRange = 10.0
    const val fireBallRange = 60
    val burnTime = ticksInt(2, TickUnit.SECONDS)

    @EventHandler
    fun onFireWandInteract(e: PlayerInteractEvent) {
        e.player.apply {
            if (WandUtil.idFrom(inventory.itemInMainHand) != FireWand.nbtId)
                return

            val dir = direction
            val origin = eyeLocation.clone()
            var loc = origin.clone()

            cancellingRepeatingSyncTask(intervalTicks = 2) {
                loc = loc.add(dir.clone { multiply(2) })
                showParticle(ParticleEffect.FLAME, loc, 1, .05)

                loc.blocksInRadius(5).filter { it.type.isFlammable && randDouble(0, 1) < FIRE_CHANCE }.forEach {
                    it.getRelative(BlockFace.UP).type = Material.FIRE
                }

                checkIfExploded(loc, origin, this@apply, this)
            }
        }
    }

    private fun checkIfExploded(
        hitPoint: Location,
        origin: Location,
        player: Player,
        task: CancellableTask
    ) {
        if (hitPoint.block.type != Material.AIR || origin dist hitPoint > fireBallRange) {
            sphere(hitPoint, 150, burstRange - 3).forEach {
                showParticle(ParticleEffect.FLAME, it, 1)
            }

            hitPoint.world?.livingEntities?.filter { it idIsNot player.id && it.location dist hitPoint <= burstRange }
                ?.forEach {
                    it.fireTicks = burnTime
                    it.damage(burstDamage)
                }
            task.cancel()
        }
    }

    fun sphere(center: Location, density: Int, radius: Double): Sequence<Location> =
        sequence {
            (0 until density).forEach { _ ->
                val u = Math.random()
                val v = Math.random()
                val theta = 2 * PI * u
                val phi = acos(2 * v - 1)
                val dx = radius * sin(phi) * cos(theta)
                val dy = radius * sin(phi) * sin(theta)
                val dz = radius * cos(phi)
                yield(center.clone { add(dx, dy, dz) })
            }
        }
}

/*
*  @Override
    public List<PParticle> getParticles(ParticlePair particle, Location location) {
        List<PParticle> particles = new ArrayList<>();

        for (int i = 0; i < this.density; i++) {
            double u = Math.random();
            double v = Math.random();
            double theta = 2 * Math.PI * u;
            double phi = Math.acos(2 * v - 1);
            double dx = this.radius * MathL.sin(phi) * MathL.cos(theta);
            double dy = this.radius * MathL.sin(phi) * MathL.sin(theta);
            double dz = this.radius * MathL.cos(phi);
            particles.add(new PParticle(location.clone().add(dx, dy, dz)));
        }

        return particles;
    }*/