package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.inventory.ItemFlag
import sharkbound.spigot.skyblock.plugin.customitems.CustomItemBase
import sharkbound.spigot.skyblock.plugin.extensions.modifyMeta
import sharkbound.spigot.skyblock.plugin.extensions.name
import sharkbound.spigot.skyblock.plugin.extensions.normalized

class GuiElement(val x: Int, val y: Int, val custom: CustomItemBase) {
    val slot = 9 * y + x
    val item = custom.createShopItem()
    val normalizedName = item.name.normalized

    init {
        item.modifyMeta { addItemFlags(ItemFlag.HIDE_ATTRIBUTES) }
    }

    override fun equals(other: Any?): Boolean {
        return other is GuiElement && other.normalizedName == normalizedName
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + item.name.normalized.hashCode()
        result = 31 * result + slot
        result = 31 * result + normalizedName.hashCode()
        return result
    }
}