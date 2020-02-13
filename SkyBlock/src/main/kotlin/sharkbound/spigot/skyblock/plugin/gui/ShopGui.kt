package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.customitems.AspectOfTheEnd
import sharkbound.spigot.skyblock.plugin.customitems.EmberRod
import sharkbound.spigot.skyblock.plugin.customitems.MobileBank
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.hasFreeInvSlot
import sharkbound.spigot.skyblock.plugin.extensions.name
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.objects.Config

@Suppress("MemberVisibilityCanBePrivate")
object ShopGui : InventoryGui("Shop", 3) {
    init {
        addElement(3, 1, EmberRod)
        addElement(4, 1, MobileBank)
        addElement(5, 1, AspectOfTheEnd)
    }

    override fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
        when (name) {
            EmberRod.shopItemName ->
                purchaseItem(player, EmberRod.createItem(), Config.emberRodCost)

            AspectOfTheEnd.shopItemName ->
                purchaseItem(player, AspectOfTheEnd.createItem(), Config.aspectOfTheEndCost)

            MobileBank.shopItemName ->
                purchaseItem(player, MobileBank.createItem(), Config.mobileBankCost)
        }
    }

    private fun hasEnoughBalance(player: Player, required: Int, itemName: String): Boolean {
        val bal = SkyBlockDatabase.balance(player)
        if (bal < required) {
            player.send("&6[SHOP] you do not have enough ${Config.currencyName} to get '$itemName&r&6', you need $required ${Config.currencyName}")
            return false
        }
        return true
    }

    private fun purchaseItem(player: Player, item: ItemStack, price: Int) {
        if (!player.hasFreeSpace() || !hasEnoughBalance(player, price, item.name)) return

        player.inventory.addItem(item)
        player.send("&eyou purchased &r${item.name}&r&e for $price ${Config.currencyName}, &eit has been added to your inventory")

        SkyBlockDatabase.modifyBalance(player, price, SkyBlockDatabase.BalanceModifyOperation.Sub)
    }

}

private fun Player.hasFreeSpace(): Boolean {
    if (!player.hasFreeInvSlot) {
        player.send("&4you do not have any free inventory slots, empty one then try to buy it again")
        return false
    }
    return true
}