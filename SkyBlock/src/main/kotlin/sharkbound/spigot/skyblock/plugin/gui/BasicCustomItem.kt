package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.customitems.CustomItemBase
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.objects.ItemTier

open class BasicCustomItem(
    val material: Material,
    name: String,
    override val itemLore: List<String> = emptyList()
) : CustomItemBase() {
    override val tier = ItemTier.NORMAL
    override val itemName = name.colored()
    override val price: Int = 0

    override fun createShopItem() =
        createItem()

    override fun createItem(): ItemStack =
        buildItem {
            material(material)
            displayName(itemName)
            lore(itemLore)
            metaFlags(ItemFlag.HIDE_ATTRIBUTES)
        }

    override fun equals(other: Any?): Boolean {
        return other is BasicCustomItem && other.material == material && other.itemName == itemName && other.price == price && other.tier == tier
    }

}
