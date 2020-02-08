package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.ifNull
import sharkbound.commonutils.extensions.isFalse
import sharkbound.commonutils.extensions.orElse
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Coords
import sharkbound.spigot.skyblock.plugin.objects.LocationHistory
import sharkbound.spigot.skyblock.plugin.utils.createSkyBlockWorld
import sharkbound.spigot.skyblock.plugin.utils.startCountDown


@Suppress("MemberVisibilityCanBePrivate")
object SkyIslandGui : InventoryGui("SkyBlock Menu", 5) {
    val DELETE = "&4Delete".colored()
    val JOIN = "&aJoin".colored()
    val RESET = "&4Reset".colored()
    val LEAVE_ISLAND = "&eLeave Island".colored()
    val SHOP = "&eShop".colored()

    init {
        addElement(1, 1, Material.GRASS, JOIN)
        addElement(7, 1, Material.BARRIER, DELETE)
        addElement(6, 1, Material.TNT, RESET)
        addElement(3, 3, Material.ARROW, LEAVE_ISLAND)
        addElement(4, 3, Material.CHEST, SHOP)
    }

    override fun clicked(player: Player, element: GuiElement, normalizedName: String, name: String) {
        when (name) {
            JOIN -> joinClicked(player)
            RESET -> resetClicked(player)
            DELETE -> deleteClicked(player)
            LEAVE_ISLAND -> leaveIslandClicked(player)
            SHOP -> shopClicked(player)
        }
    }

    private fun shopClicked(player: Player) {
        ShopGui.show(player)
    }

    private fun leaveIslandClicked(player: Player) {
        player.closeInventoryAfter {
            if (it.world.name != it.skyBlockWorldName) {
                it.send("&eleave island can only be used in your skyblock world")
                return
            }

            if (it.id !in LocationHistory) {
                it.send("&eyou do not have any previous positions to teleport to")
                return
            }


            LocationHistory.teleportBack(player)
            it.send("&ayou were teleported to your last location")
        }
    }

    private fun deleteClicked(player: Player) {
        player.closeInventoryAfter {
            if (it.skyBlockWorld?.players?.isEmpty().isFalse) {
                it.closeInventory()
                it.send("&eyour skyblock island has players in it, so it cannot be deleted right now")
                return
            }

            player.skyBlockWorld?.delete()
            player.send("&ayour skyblock world as been deleted!")
        }
    }

    private fun resetClicked(player: Player) {
        player.closeInventoryAfter {
            if (it.skyBlockWorld?.players?.isEmpty().isFalse) {
                it.send("&eyour skyblock island has players in it, so it cannot be reset right now")
                return
            }

            player.skyBlockWorld?.delete()
            createSkyBlockWorld(it)
            player.send("&ayour skyblock world as been reset!")
        }
    }

    private fun joinClicked(player: Player) {
        player.closeInventoryAfter {
            it.skyBlockWorld ifNull {
                createSkyBlockWorld(it)
            }

            startDelayedJoin(it)
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

