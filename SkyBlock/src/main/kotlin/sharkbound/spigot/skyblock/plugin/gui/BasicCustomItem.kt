package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.customitems.CustomItemBase
import sharkbound.spigot.skyblock.plugin.objects.ItemTier

open class BasicCustomItem(
    val material: Material,
    override val itemName: String,
    override val itemLore: List<String> = emptyList()
) : CustomItemBase() {
    override val tier = ItemTier.NORMAL
    override val price = 0

    override fun createShopItem(): ItemStack {
        return buildItem {
            material(material)
            displayName(itemName)
            lore(itemLore)
            metaFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
        }
    }

    override fun createItem(): ItemStack =
        createShopItem()

}
