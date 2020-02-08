package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.Material
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.utils.colorAll

internal object NbtTags {
    const val ITEM_CLASS = "itemClass"
}

enum class SpecialItemFlags(val nbtValue: String) {
    EmberRod("emberrod"),
    AspectOfTheEnd("aspectoftheend"),
}

/*internal object SpecialItemEquipMessages {
    val ASPECT_OF_THE_END =
        "&9you feel a strange power flowing into your body as you hold &r${SpecialItemNames.ASPECT_OF_THE_END} &r&9in your hand".colored()
    val EMBER_ROD =
        "&9you feel the power of the nether flow into your body as you hold &r${SpecialItemNames.EMBER_ROD} &r&9in your hand".colored()
}*/

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
        buildItem {
            material(Material.DIAMOND_SWORD)
            specialItemFlag(SpecialItemFlags.AspectOfTheEnd)
            displayName(SpecialItemNames.ASPECT_OF_THE_END)
            lore(SpecialItemLores.ASPECT_OF_THE_END)
        }

    fun emberRod() =
        buildItem {
            material(Material.BLAZE_ROD)
            specialItemFlag(SpecialItemFlags.EmberRod)
            displayName(SpecialItemNames.EMBER_ROD)
            lore(SpecialItemLores.EMBER_ROD)
        }
}
