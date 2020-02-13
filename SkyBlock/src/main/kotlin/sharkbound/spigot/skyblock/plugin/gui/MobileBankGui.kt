package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.send

object MobileBankGui : InventoryGui("Mobile Bank", 3) {

    init {
        addElement(
            2, 1, BasicCustomItem(
                Material.GOLD_INGOT, "&6Add Gold", listOf(
                    "&aHold shift to add 64 coins, click to add 1 coin"
                )
            )
        )
    }

    override fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
        player.send(e.click)
        TODO()
    }
}