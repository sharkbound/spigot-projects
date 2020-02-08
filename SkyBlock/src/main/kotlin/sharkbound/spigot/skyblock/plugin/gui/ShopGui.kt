package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.database.DB
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.hasFreeInvSlot
import sharkbound.spigot.skyblock.plugin.extensions.name
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

    private fun hasEnoughBalance(player: Player, required: Int, itemName: String): Boolean {
        val bal = DB.balance(player)
        if (bal < required) {
            player.send("&6[SHOP] you do not have enough ${Config.tokenName} to get '$itemName&r&6', you need $required ${Config.tokenName}")
            return false
        }
        return true
    }

    private fun purchaseItem(player: Player, item: ItemStack, price: Int) {
        if (!hasEnoughBalance(player, price, item.name)) return

        player.inventory.addItem(item)
        player.send("&eyou purchased &r${item.name}&r&e for $price ${Config.tokenName}, &eit has been added to your inventory")

        DB.modifyBalance(player, price, DB.BalanceModifyOperation.Sub)
    }

    private fun aspectOfTheEndClicked(player: Player) {
        if (player.hasFreeSpace()) {
            purchaseItem(player, SpecialItems.aspectOfTheEnd(), Config.aspectOfTheEndCost)
        }
    }

    private fun emberRodClicked(player: Player) {
        if (player.hasFreeSpace()) {
            purchaseItem(player, SpecialItems.emberRod(), Config.emberRodCost)
        }
    }

    fun Player.hasFreeSpace(): Boolean {
        if (!player.hasFreeInvSlot) {
            player.send("&4you do not have any free inventory slots, empty one then try to buy it again")
            return false
        }
        return true
    }
}