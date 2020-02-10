package sharkbound.spigot.skyblock.plugin.specialitems

import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.skyblock.plugin.utils.colorAll

object EmberRod {
    val color = "&a".colored()
    val itemName = "${color}Ember Rod".colored()
    val itemLore = colorAll("&r${Text.TIER}: &aSUPER")

    val shopItemName
        get() = "$itemName &e(${Config.emberRodCost} ${Config.tokenName})".colored()


    fun activate(player: Player) {
        if (!spawnEmberRodFireball(player)) {
            player.send("&4failed to create task to spawn fireball, let the server owner know if this continues to happen")
            return
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
}