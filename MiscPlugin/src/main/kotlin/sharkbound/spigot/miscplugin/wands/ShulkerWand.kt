package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ShulkerWand : Wand {
    override val nbtId = "shulkerwand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&2Shulker Seeker Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
        }.applyNBT()
}