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
    val loreColor = "&7"

    fun create(amount: Int): ItemStack =
        buildItem {
            displayName("&6Coin")
            material(Material.GOLD_NUGGET)
            lore(
                "${loreColor}&eRIGHT CLICK ${loreColor}to deposit&r&6 1 ${Config.currencyName}${loreColor} to your account",
                "${loreColor}&eSHIFT RIGHT CLICK ${loreColor}to deposit&r&6 64 ${Config.currencyName}${loreColor} to your account"
            )
            specialItemFlag(CustomItemFlag.UsableCoin)
            amount(amount)
        }

    fun use(player: Player, amount: Int, e: PlayerInteractEvent, stack: ItemStack) {
        val added = if (player.isSneaking) amount else 1
        if (amount - added == 0) {
            player.inventory.removeWhere(1) {
                it.amount == amount && it hasSpecialItemFlag CustomItemFlag.UsableCoin
            }
        } else {
            stack.amount -= added
        }

        SkyBlockDatabase.modifyBalance(player.id, added, BalanceModifyOperation.Add)
        player.send(
            "&aadded &6$added ${Config.currencyName}&a to your account, you now have &6${SkyBlockDatabase.balance(player.id)} ${Config.currencyName}"
        )
    }
}


object UsableCoinListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        e.item?.let {
            if (!it.hasSpecialItemFlag(CustomItemFlag.UsableCoin)) return

            if (e.isRightClick) {
                UsableCoin.use(e.player, it.amount, e, it)
            }
        }
    }
}