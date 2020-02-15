package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.database.BalanceModifyOperation
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.utils.colorAll

object UsableCoin {
    val loreColor = "&7"

    fun makeLore(amount: Int): List<String> =
        colorAll("${loreColor}&eRIGHT CLICK ${loreColor}to deposit&r&6 $amount ${Config.currencyName}${loreColor} to your account")

    fun updateLore(itemStack: ItemStack?) {
        if (!itemStack.hasSpecialFlag(CustomItemFlag.UsableCoin)) return
        itemStack?.let { item ->
            item.modifyMeta {
                lore = makeLore(item.amount)
            }
        }
    }

    fun create(amount: Int): ItemStack =
        buildItem {
            displayName("&6Coin")
            material(Material.GOLD_NUGGET)
            lore(makeLore(amount))
            specialItemFlag(CustomItemFlag.UsableCoin)
            amount(amount)
        }

    fun onPlayerRightClick(player: Player, amount: Int, e: PlayerInteractEvent, stack: ItemStack) {
        player.inventory.removeWhere(1) { item ->
            item.amount == amount && item hasSpecialFlag CustomItemFlag.UsableCoin
        }

        player.modifyBalance(amount, BalanceModifyOperation.Add)
        player.send(
            "&aadded &6$amount ${Config.currencyName}&a to your account, you now have &6${SkyBlockDatabase.balance(player.id)} ${Config.currencyName}"
        )
    }
}


object UsableCoinPlayerListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onCoinEquip(e: PlayerItemHeldEvent) {
        UsableCoin.updateLore(e.item)
    }

    @EventHandler
    fun onUsableCoinPickedUp(e: PlayerPickupItemEvent) {

    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        e.item?.let {
            if (!it.hasSpecialFlag(CustomItemFlag.UsableCoin)) return

            if (e.isRightClick) {
                UsableCoin.onPlayerRightClick(e.player, it.amount, e, it)
            }
        }
    }
}