package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.database.BalanceModifyOperation
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.utils.colorAll

private object MobileBankLores {
    const val nuggetLoreColor = "&7"

    val depositNugget = colorAll(
        "${nuggetLoreColor}click to deposit &r&664 ${Config.currencyName}${nuggetLoreColor} to your account"
    )

    val withdrawNugget = colorAll(
        "${nuggetLoreColor}click to withdraw &r&61 ${Config.currencyName}${nuggetLoreColor} from your account",
        "${nuggetLoreColor}shift-click to withdraw &r&664 ${Config.currencyName}${nuggetLoreColor} from your account"
    )
}

object MobileBankGui : InventoryGui("Mobile Bank", 3) {
    val deposit = addElement(3, 1, BasicCustomItem(Material.GOLD_NUGGET, "&aDeposit ${Config.currencyName}", MobileBankLores.depositNugget))
    val withdraw = addElement(5, 1, BasicCustomItem(Material.GOLD_NUGGET, "&4Withdraw ${Config.currencyName}", MobileBankLores.withdrawNugget))

    override fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
        if (element == deposit) {
            doDeposit(element, e, player)
        } else if (element == withdraw) {
            TODO()
        }
    }

    private fun doDeposit(
        element: GuiElement,
        e: InventoryClickEvent,
        player: Player
    ) {
        val needed = 64

        if (player.inventory.sumBy { if (it hasSpecialFlag CustomItemFlag.UsableCoin) it.amount else 0 } < needed) {
            player.send("&4you dont have enough &6${Config.currencyName}&4 to deposit &6$needed ${Config.currencyName}")
            return
        }

        var left = needed
        // todo add a extension function for this
        // todo test this again, to be sure it still works
        val entries = player.inventory
            .asSequence()
            .mapIndexed { slot, item -> if (item != null) IndexedInventoryItem(slot, item) else null }
            .filterNotNull()

        for (entry in entries) {
            val (slot, item) = entry
            if (!item.hasSpecialFlag(CustomItemFlag.UsableCoin)) continue
            if (item.amount == left) {
                left -= item.amount
                player.inventory.setItem(slot, null)
                break
            } else if (item.amount > left) {
                item.amount -= left
                left = 0
            } else if (item.amount < left) {
                player.inventory.setItem(slot, null)
                left -= item.amount
            }
        }

        player.modifyBalance(needed, BalanceModifyOperation.Add)
        player.send(
            "&aadded &6$needed ${Config.currencyName}&a to your account, you now have &6${SkyBlockDatabase.balance(player.id)} ${Config.currencyName}"
        )
    }

    private data class IndexedInventoryItem(val slot: Int, val item: ItemStack)
}