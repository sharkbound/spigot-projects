package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.extensions.databaseInfo
import sharkbound.spigot.skyblock.plugin.objects.Config

object BalanceItem {
    fun create(player: Player): ItemStack =
        buildItem {
            material(Material.GOLD_INGOT)
            val balance = player.databaseInfo()?.balance ?: 0
            displayName("&ayou have &e$balance ${Config.currencyName}")
            metaFlags(ItemFlag.HIDE_ATTRIBUTES)
        }
}