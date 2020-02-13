package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import sharkbound.commonutils.extensions.ifNull
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.PreSkyIslandLocation
import sharkbound.spigot.skyblock.plugin.objects.SkyIslandLocation
import sharkbound.spigot.skyblock.plugin.utils.CancellableTask
import sharkbound.spigot.skyblock.plugin.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.skyblock.plugin.utils.createSkyBlockWorld
import sharkbound.spigot.skyblock.plugin.utils.delaySyncTask


@Suppress("MemberVisibilityCanBePrivate")
object SkyIslandGui : InventoryGui("SkyBlock Menu", 5) {
    val DELETE = "&4Delete".colored()
    val JOIN = "&aJoin".colored()
    val RESET = "&4Reset".colored()
    val LEAVE_ISLAND = "&eLeave Island".colored()
    val SHOP = "&eShop".colored()

    init {
        addElement(1, 1, BasicCustomItem(Material.GRASS, JOIN))
        addElement(7, 1, BasicCustomItem(Material.BARRIER, DELETE))
        addElement(6, 1, BasicCustomItem(Material.TNT, RESET))
        addElement(3, 3, BasicCustomItem(Material.ARROW, LEAVE_ISLAND))
        addElement(4, 3, BasicCustomItem(Material.CHEST, SHOP))
    }

    override fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
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

            startDelayedLeave(it)
        }
    }

}

private fun deleteClicked(player: Player) {
    player.closeInventoryAfter {
        if (it.skyBlockWorld?.players?.isEmpty() == false) {
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
        if (it.skyBlockWorld?.players?.isEmpty() == false) {
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
            println("CREATED!")
            createSkyBlockWorld(it)
        }

        startDelayedJoin(it)
    }
}

private fun startDelayedJoin(player: Player) {
    var i = 5
    val res = cancellingRepeatingSyncTask(5.ticks, 20.ticks, { i == -1 }) {
        if (i == 0) {
            teleportPlayerToSkyIsland(player)
            player.send("&awelcome to your skyblock island!")
        } else {
            player.send("&ateleporting you to your island in &e&l$i&r&a seconds")
        }
        i -= 1
    }

    if (res.failed) {
        player.send("&4failed to join sky island")
    }
}

private fun teleportPlayerToSkyIsland(player: Player) {
    player.teleport(SkyIslandLocation.lastLocationOrDefault(player))
}


private fun startDelayedLeave(
    player: Player
): CancellableTask {
    var i = 5
    return cancellingRepeatingSyncTask(5.ticks, 20.ticks, { i == -1 }) {
        if (i == 0) {
            SkyIslandLocation.update(player.id, player.location)
            player.teleport(PreSkyIslandLocation.lastLocationOrDefault(player))

            delaySyncTask(2.secondTicks) {
                if (player.skyBlockWorld?.players?.isEmpty() == true) {
                    Bukkit.unloadWorld(player.skyBlockWorldName, true)
                }
            }

            player.send("&ayou were teleported to your last location")
        } else {
            player.send("&aleaving island in &e&l$i&r&a seconds")
        }
        i -= 1
    }
}