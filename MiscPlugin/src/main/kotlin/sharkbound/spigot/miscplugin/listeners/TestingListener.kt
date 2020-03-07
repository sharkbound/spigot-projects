package sharkbound.spigot.miscplugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.spigot.miscplugin.shared.listeners.BaseListener
import kotlin.contracts.ExperimentalContracts

object TestingListener : BaseListener() {
    @ExperimentalContracts
    @EventHandler
    fun playerInteract(e: PlayerInteractEvent) {
    }

    @EventHandler
    fun playerMove(e: PlayerMoveEvent) {

    }
}