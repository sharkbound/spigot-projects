package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Fireball
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.data.YamlCooldownBase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.objects.ItemTier
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.skyblock.plugin.utils.colorAll

private object EmberRodCooldown :
    YamlCooldownBase("ember_rod_cooldowns.yml", Config.emberRodCooldown)

object EmberRod : CustomItemBase() {
    override val itemName = "&aEmber Rod".colored()
    override val itemLore = colorAll(Text.createLoreTier(ItemTier.SUPER))
    override val tier = ItemTier.SUPER
    override val price
        get() = Config.emberRodCost

    override fun createItem() =
        buildItem {
            material(Material.BLAZE_ROD)
            specialItemFlag(CustomItemFlag.EmberRod)
            displayName(itemName)
            lore(itemLore)
        }

    override fun onPlayerUse(player: Player) {
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