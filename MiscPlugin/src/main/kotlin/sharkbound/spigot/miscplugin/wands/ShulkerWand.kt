package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object ShulkerWand : Wand {
    override val nbtId = "shulkerwand"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&2Shulker Seeker Wand"
            setWandData()
        }
}