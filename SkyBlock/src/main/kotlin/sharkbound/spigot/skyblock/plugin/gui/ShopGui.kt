package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.database.DB
import sharkbound.spigot.skyblock.plugin.extensions.hasFreeInvSlot
import sharkbound.spigot.skyblock.plugin.extensions.name
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.specialitems.AspectOfTheEnd
import sharkbound.spigot.skyblock.plugin.specialitems.EmberRod

@Suppress("MemberVisibilityCanBePrivate")
object ShopGui : InventoryGui("Shop", 3) {
    init {
        addElement(3, 1, Material.BLAZE_ROD, EmberRod.shopItemName, EmberRod.itemLore)
        addElement(5, 1, Material.DIAMOND_SWORD, AspectOfTheEnd.shopItemName, AspectOfTheEnd.itemLore)
    }

    override fun clicked(player: Player, element: GuiElement, normalizedName: String, name: String) {
        when (name) {
            EmberRod.shopItemName ->
                purchaseItem(player, EmberRod.finalItem(), Config.emberRodCost)

            AspectOfTheEnd.shopItemName ->
                purchaseItem(player, AspectOfTheEnd.finalItem(), Config.aspectOfTheEndCost)
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
        if (!player.hasFreeSpace() || !hasEnoughBalance(player, price, item.name)) return

        player.inventory.addItem(item)
        player.send("&eyou purchased &r${item.name}&r&e for $price ${Config.tokenName}, &eit has been added to your inventory")

        DB.modifyBalance(player, price, DB.BalanceModifyOperation.Sub)
    }

}

private fun Player.hasFreeSpace(): Boolean {
    if (!player.hasFreeInvSlot) {
        player.send("&4you do not have any free inventory slots, empty one then try to buy it again")
        return false
    }
    return true
}