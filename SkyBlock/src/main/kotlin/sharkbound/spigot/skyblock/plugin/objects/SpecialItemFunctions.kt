package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.extensions.spawnCast
import sharkbound.spigot.skyblock.plugin.extensions.target
import sharkbound.spigot.skyblock.plugin.extensions.y

object SpecialItemFunctions {
    fun emberRod(player: Player) {
        player.world.spawnCast<Fireball>(
            player.location.add(player.location.direction.multiply(2)).add(1.y)
        ).velocity.add(player.location.direction.multiply(2))
    }

    fun aspectOfTheEnd(player: Player) {
        player.teleport(player.target(Config.aspectOfTheEndRange).location)
        player.send("&5Poof!")
    }
}