package sharkbound.spigot.skyblock.plugin.objects

import sharkbound.spigot.skyblock.plugin.extensions.colored

object Text {
    val TIER = "&7Tier".colored()

    fun createShopItemName(itemName: String, price: Int) =
        "&r$itemName &r&e(${price} ${Config.currencyName})".colored()

    fun createLoreTier(tier: ItemTier) =
        "&r${TIER}: $tier&r"
}