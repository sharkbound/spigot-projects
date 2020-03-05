package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

val PlayerInteractEvent.isLeftClick
    get() = action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR

val PlayerInteractEvent.isRightClick
    get() = action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR