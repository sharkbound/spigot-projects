package sharkbound.spigot.skyblock.plugin.specialitems

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.data.YamlCooldownBase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.ItemTier
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemFlags
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.skyblock.plugin.utils.colorAll

private object EmberRodCooldown :
    YamlCooldownBase("ember_rod_cooldowns.yml", Config.emberRodCooldown)

object EmberRod {
    val color = "&a".colored()
    val itemName = "${color}Ember Rod".colored()
    val itemLore = colorAll("&r${Text.TIER}: ${ItemTier.SUPER}")

    val shopItemName
        get() = "$itemName &e(${Config.emberRodCost} ${Config.currencyName})".colored()

    fun finalItem() =
        buildItem {
            material(Material.BLAZE_ROD)
            specialItemFlag(SpecialItemFlags.EmberRod)
            displayName(itemName)
            lore(itemLore)
        }

    fun activate(player: Player) {
        if (EmberRodCooldown.onCooldown(player.id)) {
            player.itemError("you must wait ${EmberRodCooldown.remainingCooldownFormatted(player.id)} seconds to use this item again")
            return
        }

        if (spawnEmberRodFireball(player)) {
            player.playSound(player.location, Sound.FIRE_IGNITE, .5f, 1f)
            EmberRodCooldown.update(player.id)
        } else {
            player.itemError("&4failed to create task to spawn fireball, let the server owner know if this continues to happen")
        }
    }

    private fun Player.itemError(msg: String, errorColor: String = "&3"): Boolean {
        player.send("$errorColor[&r${itemName}$errorColor] $msg")
        return false
    }

    private fun spawnEmberRodFireball(player: Player): Boolean {
        val fireball = player.world.spawnCast<Fireball>(
            player.location.add(player.lookDirection.multiply(2).add(1.y))
        )

        val lookDir = player.lookDirection.multiply(3)
        return cancellingRepeatingSyncTask(0.ticks, 20.ticks, fireball::isDead) {
            fireball.velocity = lookDir
        }.successful
    }
}