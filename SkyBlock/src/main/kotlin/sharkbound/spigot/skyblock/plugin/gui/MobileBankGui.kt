package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.utils.colorAll

private object MobileBankLores {
    const val nuggetLoreColor = "&7"

    val depositNugget = colorAll(
        "${nuggetLoreColor}click to deposit &r&61 ${Config.currencyName}${nuggetLoreColor} to your account",
        "${nuggetLoreColor}shift-click to deposit &r&664 ${Config.currencyName}${nuggetLoreColor} to your account"
    )

    val withdrawNugget = colorAll(
        "${nuggetLoreColor}click to withdraw &r&61 ${Config.currencyName}${nuggetLoreColor} from your account",
        "${nuggetLoreColor}shift-click to withdraw &r&664 ${Config.currencyName}${nuggetLoreColor} from your account"
    )
}

object MobileBankGui : InventoryGui("Mobile Bank", 3) {
    init {
        addElement(3, 1, BasicCustomItem(Material.GOLD_NUGGET, "&aDeposit ${Config.currencyName}", MobileBankLores.depositNugget))
        addElement(5, 1, BasicCustomItem(Material.GOLD_NUGGET, "&4Withdraw ${Config.currencyName}", MobileBankLores.withdrawNugget))
    }

    override fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
        // todo
    }
}