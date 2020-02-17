package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.customitems.BalanceItem
import sharkbound.spigot.skyblock.plugin.customitems.UsableCoin
import sharkbound.spigot.skyblock.plugin.database.BalanceModifyOperation
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.utils.colorAll

object MobileBankGui : InventoryGui("Mobile Bank", 3) {
    val deposit = addElement(
        3, 1, BasicCustomItem(
            Material.GOLD_NUGGET, "&aDeposit ${Config.currencyName}", colorAll(
                "&7click to deposit &r&664 ${Config.currencyName}&7 to your account"
            )
        )
    )
    val withdraw = addElement(
        5, 1, BasicCustomItem(
            Material.GOLD_NUGGET, "&4Withdraw ${Config.currencyName}",
            colorAll(
                "&7click to withdraw &r&664 ${Config.currencyName}&7 from your account"
            )
        )
    )

    override fun prepareInventory(player: Player) =
        super.prepareInventory(player).also { it[4, 2] = BalanceItem.create(player) }

    override fun customClickHandler(e: InventoryClickEvent, player: Player, itemName: String?, item: ItemStack?, element: GuiElement?): Boolean =
        (item?.let { "you have" in it.name } ?: false).also {
            if (it) e.cancel()
        }

    override fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
        if (element == deposit) {
            doDeposit(player)
        } else if (element == withdraw) {
            doWithdraw(player)
        }
    }

    private const val WITHDRAW_AMOUNT = 64
    private fun doWithdraw(player: Player) {
        val data = player.databaseInfo()
        if (data == null || data.balance == 0) {
            player.send("&4you do not have any &6${Config.currencyName}")
            return
        }

        if (data.balance < WITHDRAW_AMOUNT) {
            player.send("&4you need at minimum &664 ${Config.currencyName}&4 in your balance to withdraw")
            return
        }

        player.modifyBalance(WITHDRAW_AMOUNT, BalanceModifyOperation.Sub)
        player.inventory.addItem(UsableCoin.create(WITHDRAW_AMOUNT))

        player.send("&ayou withdrew &6$WITHDRAW_AMOUNT ${Config.currencyName}")
    }

    private fun doDeposit(
        player: Player
    ) {
        val needed = 64

        if (player.inventory.sumBy { if (it hasSpecialFlag CustomItemFlag.UsableCoin) it.amount else 0 } < needed) {
            player.send("&4you dont have enough &6${Config.currencyName}&4 in your inventory to deposit &6$needed ${Config.currencyName}")
            return
        }

        var left = needed
        loop@ for (entry in player.inventory.indexed) {
            val (slot, item) = entry
            if (!item.hasSpecialFlag(CustomItemFlag.UsableCoin)) continue

            when {
                item.amount == left -> {
                    left -= item.amount
                    player.inventory.setNull(slot)
                    break@loop
                }
                item.amount > left -> {
                    item.amount -= left
                    break@loop
                }
                item.amount < left -> {
                    player.inventory.setItem(slot, null)
                    left -= item.amount
                }
            }
        }

        player.modifyBalance(needed, BalanceModifyOperation.Add)
        player.send(
            "&aadded &6$needed ${Config.currencyName}&a to your account"
        )
    }
}

object MobileBankListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onBankPlaced(e: BlockPlaceEvent) {
        if (e.itemInHand hasSpecialFlag CustomItemFlag.MobileBank) {
            e.cancel()
        }
    }
}