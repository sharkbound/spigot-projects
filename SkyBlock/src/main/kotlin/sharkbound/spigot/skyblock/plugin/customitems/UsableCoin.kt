package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.data.YamlCooldownBase
import sharkbound.spigot.skyblock.plugin.database.BalanceModifyOperation
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag

object UsableCoinCooldown : YamlCooldownBase("usablecoincooldowns.yml", Config.usableCoinCooldown)

object UsableCoin {
    val coinName = "&6Coin"

    fun create(amount: Int): ItemStack {
        return buildItem {
            displayName(coinName)
            material(Material.GOLD_NUGGET)
            lore(
                "&7&eRIGHT CLICK&7 to redeem these coins",
                "&7&eSNEAK RIGHT CLICK&7 to redeem all coins in your inventory"
            )
            specialItemFlag(CustomItemFlag.UsableCoin)
            amount(amount)
        }
    }

    private fun Player.itemError(msg: String, errorColor: String = "&3"): Boolean {
        player.send("$errorColor[&r${coinName}$errorColor] $msg")
        return false
    }

    fun onPlayerRightClick(player: Player, amount: Int, e: PlayerInteractEvent) {
        if (UsableCoinCooldown.onCooldown(player.id)) {
            UsableCoinCooldown.remainingCooldownFormatted(player.id).let {
                player.itemError("you must wait $it more seconds to redeem more coins")
            }
            return
        }

        when {
            player.isSneaking ->
                player.inventory.sumBy { if (it hasSpecialFlag CustomItemFlag.UsableCoin) it.amount else 0 }.also {
                    player.inventory.removeWhere { it hasSpecialFlag CustomItemFlag.UsableCoin }
                }
            else -> e.item.amount.also { _ -> player.inventory.removeWhere(1) { it == e.item } }
        }.let {
            player.modifyBalance(it, BalanceModifyOperation.Add)
            player.send("&aadded &6$it ${Config.currencyName}&a to your account")
        }

        UsableCoinCooldown.update(player.id)
    }
}


object UsableCoinPlayerListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        e.item?.let {
            if (!it.hasSpecialFlag(CustomItemFlag.UsableCoin)) return

            if (e.isRightClick) {
                UsableCoin.onPlayerRightClick(e.player, it.amount, e)
            }
        }
    }
}