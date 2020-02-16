package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.database.BalanceModifyOperation
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag

object UsableCoin {
    fun create(amount: Int): ItemStack =
        buildItem {
            displayName("&6Coin")
            material(Material.GOLD_NUGGET)
            lore(
                "&7&eRIGHT CLICK&7 to redeem these coins",
                "&7&eSNEAK RIGHT CLICK&7 to redeem all coins in your inventory"
            )
            specialItemFlag(CustomItemFlag.UsableCoin)
            amount(amount)
        }

    fun onPlayerRightClick(player: Player, amount: Int, e: PlayerInteractEvent) {
        when {
            player.isSneaking -> {
                player.inventory.sumBy { if (it hasSpecialFlag CustomItemFlag.UsableCoin) it.amount else 0 }.also { _ ->
                    player.inventory.removeWhere { it hasSpecialFlag CustomItemFlag.UsableCoin }
                }
            }
            else -> {
                player.inventory.removeWhere(1) { it == e.item }
                e.item.amount
            }
        }.let {
            player.modifyBalance(it, BalanceModifyOperation.Add)
            player.send("&aadded &6$it ${Config.currencyName}&a to your account")
        }
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