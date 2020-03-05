package sharkbound.spigot.miscplugin.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object PhantomPortal : Wand {
    override val nbtId = "phantomportal"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&2Phantom Portal Wand"
            enchant(Enchantment.PROTECTION_FIRE, 1)
            hideEnchants()
        }.applyNBT()
}