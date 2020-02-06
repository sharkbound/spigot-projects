package sharkbound.spigot.skyblock.plugin.inventories

import io.netty.handler.codec.http.multipart.AbstractDiskHttpData
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import sharkbound.commonutils.extensions.ifNull
import sharkbound.commonutils.extensions.isFalse
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Coords
import sharkbound.spigot.skyblock.plugin.objects.LocationHistory
import sharkbound.spigot.skyblock.plugin.utils.createSkyBlockWorld
import sharkbound.spigot.skyblock.plugin.utils.startCountDown


object SkyIslandGui : InventoryGui("SkyBlock Menu", 5) {
    val DELETE = "&4Delete".colored()
    val JOIN = "&aJoin".colored()
    val RESET = "&4Reset".colored()
    val EXIT = "&eExit".colored()

    init {
        addElements(
            GuiElement(1, 1, Material.GRASS, JOIN),
            GuiElement(7, 1, Material.BARRIER, DELETE),
            GuiElement(6, 1, Material.TNT, RESET),
            GuiElement(4, 2, Material.ARROW, EXIT)
        )
    }

    override fun clicked(player: Player, element: GuiElement, normalizedName: String, name: String) {
        when (name) {
            JOIN -> {
                player.skyBlockWorld ifNull {
                    createSkyBlockWorld(player)
                }


                player.closeInventory()
                startDelayedJoin(player)
            }

            RESET -> {
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

            DELETE -> {
                if (player.skyBlockWorld?.players?.isEmpty().isFalse) {
                    player.closeInventory()
                    player.send("&eyour skyblock island has players in it, so it cannot be deleted right now")
                    return
                }

                player.skyBlockWorld?.delete()
                player.closeInventory()
                player.send("&ayour skyblock world as been deleted!")
            }

            EXIT -> {
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

