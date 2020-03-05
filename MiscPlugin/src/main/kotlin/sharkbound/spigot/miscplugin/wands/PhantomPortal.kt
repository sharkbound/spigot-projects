package sharkbound.spigot.miscplugin.wands

import org.bukkit.Material
import sharkbound.spigot.miscplugin.shared.builders.buildItem

object PhantomPortal : Wand {
    override val nbtId = "phantomportal"

    override fun create() =
        buildItem(Material.STICK) {
            name = "&2Phantom Portal Wand"
            setWandData()
        }
}