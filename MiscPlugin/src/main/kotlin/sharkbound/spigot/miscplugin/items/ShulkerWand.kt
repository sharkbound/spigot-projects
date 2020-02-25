package sharkbound.spigot.miscplugin.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ShulkerWand {
    val nbtId = "shulkerwand"

    fun create() =
        buildItem(Material.STICK) {
            name = "&2Shulker Seeker Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
            nbt {
                setString("type", nbtId)
            }
        }
}