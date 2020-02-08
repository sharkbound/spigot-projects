package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.displayName
import sharkbound.spigot.skyblock.plugin.extensions.modifyMeta
import sharkbound.spigot.skyblock.plugin.utils.colorAll


internal object SpecialItemNames {
    val ASPECT_OF_THE_END = "&5Aspect Of The End".colored()
    val EMBER_ROD = "&aEmber Rod".colored()
}

internal object SpecialItemLores {
    val ASPECT_OF_THE_END = colorAll("&r${Text.TIER}: &5EPIC")
    val EMBER_ROD = colorAll("&r${Text.TIER}: &aSUPER")
}

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
