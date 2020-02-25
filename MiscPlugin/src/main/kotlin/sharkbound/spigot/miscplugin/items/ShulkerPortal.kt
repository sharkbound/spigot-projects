package sharkbound.spigot.miscplugin.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ShulkerPortal {
    const val nbtId = "shulkerportal"

    fun create() =
        buildItem(Material.BLAZE_ROD) {
            name = "&2Shulker Portal Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
            nbt {
                setString("type", nbtId)
            }
        }
}