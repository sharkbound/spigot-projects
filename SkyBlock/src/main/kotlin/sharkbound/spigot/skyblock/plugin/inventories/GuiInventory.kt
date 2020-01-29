package sharkbound.spigot.skyblock.plugin.inventories

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.Coords
import sharkbound.spigot.skyblock.plugin.extensions.delete
import sharkbound.spigot.skyblock.plugin.extensions.displayName
import sharkbound.spigot.skyblock.plugin.extensions.sendColored
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorld
import sharkbound.spigot.skyblock.plugin.utils.createSkyBlockWorld


data class GuiElement(val slot: Int, val material: Material, val name: String) {
    val item: ItemStack = ItemStack(material, 1).displayName(name)
}

object GuiButtons {
    val delete = "Delete"
    val join = "Join"
    val reset = "Reset"

    val allNames = setOf(delete, join, reset)
}

object GuiNames {
    val skyBlockGuiMain = "SkyBlock Menu"

    val allNames = setOf(skyBlockGuiMain)
}

object SkyBlockGui {
    fun show(player: Player) {
        player.openInventory(create(player))
    }

    fun clicked(player: Player, name: String) {
        when (name) {
            GuiButtons.join -> {
                if (player.skyBlockWorld == null) {
                    createSkyBlockWorld(player)
                }
                player.teleport(
                    Location(
                        player.skyBlockWorld,
                        0.0,
                        Coords.SKY_ISLAND_SCHEMATIC_Y,
                        0.0,
                        player.location.yaw,
                        player.location.pitch
                    ).add(Coords.skyIslandSpawnOffset)
                )
            }
            GuiButtons.reset -> {
                player.skyBlockWorld?.delete()
                createSkyBlockWorld(player)
                player.sendColored("&ayour skyblock world as been reset!")
            }
            GuiButtons.delete -> {
                if (player.skyBlockWorld?.players?.isEmpty() == true) {
                    return
                }

                player.skyBlockWorld?.delete()
                player.sendColored("&ayour skyblock world as been deleted!")
            }
        }
    }

    private fun create(player: Player): Inventory {
        val items = arrayOf(
            GuiElement(0, Material.DIAMOND_PICKAXE, GuiButtons.join),
            GuiElement(1, Material.BARRIER, GuiButtons.delete),
            GuiElement(2, Material.TNT, GuiButtons.reset)
        )

        val gui = Bukkit.createInventory(player, 9 * 3, GuiNames.skyBlockGuiMain)

        items.forEach {
            gui.setItem(it.slot, it.item)
        }

        return gui
    }
}