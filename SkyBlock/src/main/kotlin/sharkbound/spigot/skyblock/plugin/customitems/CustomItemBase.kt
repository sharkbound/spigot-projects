package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.displayName
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.objects.ItemTier
import sharkbound.spigot.skyblock.plugin.objects.Text

abstract class CustomItemBase {
    abstract val itemName: String
    abstract val itemLore: List<String>
    abstract val tier: ItemTier
    abstract val price: Int

    val shopItemName
        get() = Text.createShopItemName(itemName, price)

    abstract fun createItem(): ItemStack

    open fun onPlayerUse(player: Player) {}

    open fun createShopItem(): ItemStack =
        createItem().displayName(Text.createShopItemName(itemName, price))

    protected open fun Player.itemError(msg: String, errorColor: String = "&3"): Boolean {
        player.send("$errorColor[&r${itemName}$errorColor] $msg")
        return false
    }

    override fun toString(): String {
        return "<${javaClass.simpleName} itemName=$itemName tier=$tier> price=$price"
    }
}