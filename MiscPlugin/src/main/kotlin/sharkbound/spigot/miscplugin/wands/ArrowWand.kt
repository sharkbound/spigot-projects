package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ArrowWand : Wand {
    override val nbtId = "arrowwand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&2Arrow Wand"
            setWandData()
        }
}