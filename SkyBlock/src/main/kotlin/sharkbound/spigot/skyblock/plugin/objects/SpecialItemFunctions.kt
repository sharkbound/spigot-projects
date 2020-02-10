package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.len
import sharkbound.commonutils.toMaybe
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.utils.cancellingRepeatingSyncTask

object SpecialItemFunctions {
    fun emberRod(player: Player) {
        if (!spawnEmberRodFireball(player)) {
            player.send("&4failed to creation task to spawn fireball, let the server owner know if this continues to happen")
            return
        }
    }

    fun aspectOfTheEnd(player: Player) {
//        player.teleport(player.target(Config.aspectOfTheEndRange).location.apply {
//            pitch = player.pitch
//            yaw = player.yaw
//        })
        try {
            player.lastTwoTarget().toMaybe.filter { it.len == 2 }.apply {
                ifPresent {
                    player.send("${it[1].getFace(it[0])}")
                }.ifAbsent {
                    player.send(":(")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        player.send("&5Poof!")
    }
}

private fun spawnEmberRodFireball(player: Player): Boolean {
    val fireball = player.world.spawnCast<Fireball>(
        player.location.add(player.lookDirection.multiply(3).add(1.y))
    )

    val lookDir = player.lookDirection.multiply(3)
    return cancellingRepeatingSyncTask(0.ticks, 20.ticks, fireball::isDead) {
        fireball.velocity = lookDir
    }.successful
}