package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.utils.TaskResult
import sharkbound.spigot.skyblock.plugin.utils.repeatingSyncTask

object SpecialItemFunctions {
    fun emberRod(player: Player) {
        val fireball = player.world.spawnCast<Fireball>(
            player.location.add(player.lookDirection.multiply(3).add(1.y))
        )

        val lookDir = player.lookDirection.multiply(3)
        var task = TaskResult(-1)
        task = repeatingSyncTask(2.ticks, 6.ticks) {
            if (fireball.isDead) {
                task.cancel()
            }
            fireball.velocity = lookDir
        }
    }

    fun aspectOfTheEnd(player: Player) {
        player.teleport(player.target(Config.aspectOfTheEndRange).location.apply {
            pitch = player.pitch
            yaw = player.yaw
        })
        player.send("&5Poof!")
    }
}