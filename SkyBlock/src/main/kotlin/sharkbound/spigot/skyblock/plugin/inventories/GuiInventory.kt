package sharkbound.spigot.skyblock.plugin.inventories

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import sharkbound.commonutils.extensions.isFalse
import sharkbound.spigot.skyblock.plugin.objects.Coords
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.LocationHistory
import sharkbound.spigot.skyblock.plugin.utils.createSkyBlockWorld
import sharkbound.spigot.skyblock.plugin.utils.startCountDown


data class GuiElement(val slot: Int, val material: Material, val name: String) {
    val item: ItemStack = ItemStack(material, 1).displayName(name)
}

object GuiButtons {
    val delete = "&4Delete".colored()
    val join = "&aJoin".colored()
    val reset = "&4Reset".colored()
    val exit = "&eExit".colored()

    val allNames = setOf(delete, join, reset, exit)
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
            GuiElement(9 + 6, Material.TNT, GuiButtons.reset),
            GuiElement(18 + 4, Material.ARROW, GuiButtons.exit)
        )

        return Bukkit.createInventory(player, 9 * 4, GuiNames.skyBlockGuiMain).apply {
            items.forEach {
                setItem(it.slot, it.item)
            }
        }
    }

    fun clicked(player: Player, name: String) {
        when (name) {
            GuiButtons.join -> {
                if (player.skyBlockWorld == null) {
                    createSkyBlockWorld(player)
                }

                player.closeInventory()
                startDelayedJoin(player)
            }

            GuiButtons.reset -> {
                if (player.skyBlockWorld?.players?.isEmpty().isFalse) {
                    player.closeInventory()
                    player.send("&eyour skyblock island has players in it, so it cannot be reset right now")
                    return
                }

                player.skyBlockWorld?.delete()
                createSkyBlockWorld(player)
                player.closeInventory()
                player.send("&ayour skyblock world as been reset!")
            }

            GuiButtons.delete -> {
                if (player.skyBlockWorld?.players?.isEmpty().isFalse) {
                    player.closeInventory()
                    player.send("&eyour skyblock island has players in it, so it cannot be deleted right now")
                    return
                }

                player.skyBlockWorld?.delete()
                player.closeInventory()
                player.send("&ayour skyblock world as been deleted!")
            }

            GuiButtons.exit -> {
                if (player.id !in LocationHistory) {
                    player.closeInventory()
                    player.send("&eyou do not have any previous positions to teleport to")
                    return
                }

                if (player.world.name != player.skyBlockWorldName) {
                    player.closeInventory()
                    player.send("&ethis can only be used in your skyblock world")
                    return
                }

                LocationHistory.teleportBack(player)
                player.send("&ayou were teleported to your last location")
            }
        }
    }

    private fun startDelayedJoin(player: Player) {
        val res = startCountDown(5, 10.ticks) {
            if (it == 0) {
                teleportPlayerToSkyIsland(player)
                player.send("&awelcome to your skyblock island!")
            } else {
                player.send("&ateleporting you to your island in &e&l$it &r&aseconds")
            }
        }

        if (res.failed) {
            player.send("&4failed to join sky island")
        }
    }

    private fun teleportPlayerToSkyIsland(player: Player) {
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
}