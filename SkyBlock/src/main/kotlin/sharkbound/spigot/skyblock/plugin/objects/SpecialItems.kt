package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.displayName
import sharkbound.spigot.skyblock.plugin.extensions.modifyMeta

internal object SpecialItems {
    fun aspectOfTheEnd() =
        ItemStack(Material.DIAMOND_SWORD).apply {
            displayName(SpecialItemNames.ASPECT_OF_THE_END)
            modifyMeta {
                lore = SpecialItemLores.ASPECT_OF_THE_END
            }
        }

    fun emberRod() =
        ItemStack(Material.BLAZE_ROD).apply {
            displayName(SpecialItemNames.EMBER_ROD)
            modifyMeta {
                lore = SpecialItemLores.EMBER_ROD
            }
        }
}