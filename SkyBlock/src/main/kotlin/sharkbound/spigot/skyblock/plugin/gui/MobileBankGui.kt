package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.objects.Config

object MobileBankGui : InventoryGui("Mobile Bank", 3) {

    init {
        val moneyName = Config.currencyName
        "&7".let { color ->
            addElement(
                3, 1, BasicCustomItem(
                    Material.GOLD_NUGGET, "&aDeposit $moneyName", listOf(
                        "${color}click to deposit &r&61 ${moneyName}${color} to your account",
                        "${color}shift-click to deposit &r&664 ${moneyName}${color} to your account"
                    )
                )
            )
            addElement(
                5, 1, BasicCustomItem(
                    Material.GOLD_NUGGET, "&4Withdraw $moneyName", listOf(
                        "${color}click to withdraw &r&61 ${moneyName}${color} from your account",
                        "${color}shift-click to withdraw &r&664 ${moneyName}${color} from your account"
                    )
                )
            )
        }
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