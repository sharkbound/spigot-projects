package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.database.DB
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.hasFreeInvSlot
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemLores
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemNames
import sharkbound.spigot.skyblock.plugin.objects.SpecialItems

@Suppress("MemberVisibilityCanBePrivate")
object ShopGui : InventoryGui("Shop", 3) {
    val EMBER_ROD_SHOP_ITEM_NAME
        get() = "${SpecialItemNames.EMBER_ROD} &e(${Config.emberRodCost} ${Config.tokenName})".colored()

    val ASPECT_OF_THE_END_SHOP_ITEM_NAME
        get() = "${SpecialItemNames.ASPECT_OF_THE_END} &e(${Config.aspectOfTheEndCost} ${Config.tokenName})".colored()

    init {
        addElement(3, 1, Material.BLAZE_ROD, EMBER_ROD_SHOP_ITEM_NAME, SpecialItemLores.EMBER_ROD)
        addElement(5, 1, Material.DIAMOND_SWORD, ASPECT_OF_THE_END_SHOP_ITEM_NAME, SpecialItemLores.ASPECT_OF_THE_END)
    }

    override fun clicked(player: Player, element: GuiElement, normalizedName: String, name: String) {
        when (name) {
            EMBER_ROD_SHOP_ITEM_NAME -> emberRodClicked(player)
            ASPECT_OF_THE_END_SHOP_ITEM_NAME -> aspectOfTheEndClicked(player)
        }
    }

    private fun sendNotEnoughFunds(player: Player, required: Int, currentBalance: Int, itemName: String) {
        val color = "&6"
        player.send("&6[SHOP]${color} you do not have enough ${Config.tokenName} to get $itemName&r${color}, you short by ${required - currentBalance} ${Config.tokenName}")
    }

    private fun aspectOfTheEndClicked(player: Player) {
        val bal = DB.balance(player)
        if (bal < Config.aspectOfTheEndCost) {
            sendNotEnoughFunds(player, Config.aspectOfTheEndCost, bal, "&5Aspect Of The End")
            return
        }

        if (!player.hasFreeInvSlot) {
            player.send("&4you do not have any free inventory slots, please empty a slot then try to buy it again")
            return
        }

        player.closeInventory()
        purchaseItem(player, SpecialItems.aspectOfTheEnd(), Config.aspectOfTheEndCost)
    }

    private fun purchaseItem(player: Player, item: ItemStack, price: Int) {
        player.inventory.addItem(item)

        val itemName = item.itemMeta?.displayName ?: item.type.name
        player.send("&eyou purchased &r${itemName}&r, &eit has been added to your inventory")

        DB.modifyBalance(player, price, DB.BalanceModifyMode.Sub)
    }

    private fun emberRodClicked(player: Player) {
        TODO()
    }
}