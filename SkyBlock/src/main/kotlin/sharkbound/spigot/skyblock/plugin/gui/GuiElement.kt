package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.displayName
import sharkbound.spigot.skyblock.plugin.extensions.modifyMeta
import sharkbound.spigot.skyblock.plugin.extensions.normalized

class GuiElement(val x: Int, val y: Int, val material: Material, val name: String) {
    val slot = 9 * y + x
    val normalizedName = name.normalized
    val item = ItemStack(material, 1).displayName(name)

    init {
        item.modifyMeta { addItemFlags(ItemFlag.HIDE_ATTRIBUTES) }
    }

    fun lore(loreLines: List<String>) =
        item.modifyMeta { lore = loreLines.map { it.colored() } }

    override fun equals(other: Any?): Boolean {
        return other is GuiElement && other.normalizedName == normalizedName
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + name.hashCode()
        result = 31 * result + slot
        result = 31 * result + normalizedName.hashCode()
        return result
    }
}