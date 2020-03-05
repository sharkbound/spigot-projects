package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ShulkerPortal : Wand {
    override val nbtId = "shulkerportal"

    override fun create() =
        buildItem(Material.BLAZE_ROD) {
            name = "&2Shulker Portal Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
        }.applyNBT()
}