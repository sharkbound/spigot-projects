package sharkbound.spigot.miscplugin.listeners

import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.miscplugin.shared.extensions.*

object PlayerEffectSpawner : Listener {
    init {
        registerEvents()
    }

    var spawn = 0

    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        spawn += 1
        if (spawn % 10 != 0) return

        fun rbg() =
            Color.fromRGB(randInt(1, 255), randInt(1, 255), randInt(1, 255))


        e.player.world.spawnAs<Firework>(e.player.eyeLocation).apply {
            fireworkMeta = fireworkMeta.apply {
                power = 1
                addEffect(
                    FireworkEffect.builder()
                        .withColor(rbg())
                        .trail(false)
                        .with(FireworkEffect.Type.BURST)
                        .build()
                )
            }
            velocity = e.player.direction
        }
    }
}