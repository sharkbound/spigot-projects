package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ShulkerPortal : Wand {
    override val nbtId = "shulkerportal"

    override fun create() =
        buildItem(Material.BLAZE_ROD) {
            name = "&2Shulker Portal Wand"
            setWandData()
        }
}