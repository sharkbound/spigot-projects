package sharkbound.spigot.skyblock.plugin.inventories

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.Coords
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.utils.createSkyBlockWorld


data class GuiElement(val slot: Int, val material: Material, val name: String) {
    val item: ItemStack = ItemStack(material, 1).displayName(name)
}

object GuiButtons {
    val delete = "&4Delete".colorFormat()
    val join = "&aJoin".colorFormat()
    val reset = "&4Reset".colorFormat()

    val allNames = setOf(delete, join, reset)
}

object GuiNames {
    val skyBlockGuiMain = "SkyBlock Menu"

    val allNames = setOf(skyBlockGuiMain)
}

object SkyBlockMainGui {
    fun show(player: Player) {
        player.openInventory(create(player))
    }

    private fun create(player: Player): Inventory {
        val items = arrayOf(
            GuiElement(9 + 1, Material.GRASS, GuiButtons.join),
            GuiElement(9 + 7, Material.BARRIER, GuiButtons.delete),
            GuiElement(9 + 6, Material.TNT, GuiButtons.reset)
        )

        val gui = Bukkit.createInventory(player, 9 * 3, GuiNames.skyBlockGuiMain)

        items.forEach {
            gui.setItem(it.slot, it.item)
        }

        return gui
    }

    fun clicked(player: Player, name: String) {
        println(name)
        when (name) {
            // region join
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
                player.send("&awelcome to your skyblock island!")
            }
            // endregion
            //region reset
            GuiButtons.reset -> {
                if (player.skyBlockWorld?.players?.isEmpty() == false) {
                    player.send("&eyour skyblock island has players in it, so it cannot be reset right now")
                    return
                }

                player.skyBlockWorld?.delete()
                createSkyBlockWorld(player)
                player.send("&ayour skyblock world as been reset!")
            }
            //endregion
            //region delete
            GuiButtons.delete -> {
                if (player.skyBlockWorld?.players?.isEmpty() == false) {
                    player.send("&eyour skyblock island has players in it, so it cannot be deleted right now")
                    return
                }

                player.skyBlockWorld?.delete()
                player.send("&ayour skyblock world as been deleted!")
            }
            //endregion
        }
    }
}