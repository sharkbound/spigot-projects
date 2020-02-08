package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.*

object SpecialItemFunctions {
    fun emberRod(player: Player) {
        player.world.spawnCast<Fireball>(
            player.location.add(player.lookDirection.multiply(2)).add(1.y)
        ).velocity.add(player.lookDirection.multiply(2))
    }

    fun aspectOfTheEnd(player: Player) {
        player.teleport(player.target(Config.aspectOfTheEndRange).location.apply {
            pitch = player.pitch
            yaw = player.yaw
        })
        player.send("&5Poof!")
    }
}