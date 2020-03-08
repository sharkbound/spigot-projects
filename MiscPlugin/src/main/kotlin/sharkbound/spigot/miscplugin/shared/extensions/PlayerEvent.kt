package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.PlayerInventory

val PlayerEvent.inv: PlayerInventory
    get() = player.inventory

inline infix fun <R> PlayerEvent.player(block: Player.() -> R): R =
    player.run(block)