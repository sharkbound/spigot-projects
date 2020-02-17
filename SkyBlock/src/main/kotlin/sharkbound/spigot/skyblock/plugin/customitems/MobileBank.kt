package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.gui.MobileBankGui
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.objects.ItemTier
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.colorAll

object MobileBank : CustomItemBase() {
    val playerSkullTypeId = 3.toShort()
    val color = "&6".colored()

    override val itemName = "${color}Mobile Bank".colored()
    override val itemLore = colorAll(Text.createLoreTier(ItemTier.NORMAL))
    override val tier = ItemTier.NORMAL
    override val price
        get() = Config.mobileBankCost

    override fun createItem(): ItemStack {
        return buildItem {
            displayName(itemName)
            material(Material.SKULL_ITEM)
            specialItemFlag(CustomItemFlag.MobileBank)
            metaFlags(ItemFlag.HIDE_ATTRIBUTES)
            metaCast<SkullMeta> { owner = Config.mobileBankSkullOwnerUserName }
//          skulls use durability to determine their type
            durability(playerSkullTypeId)
            lore(Text.createLoreTier(tier))
        }
    }

    override fun onPlayerUse(player: Player) {
        MobileBankGui.show(player)
    }

}