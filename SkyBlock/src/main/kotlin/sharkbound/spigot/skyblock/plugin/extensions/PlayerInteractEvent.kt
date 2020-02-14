package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.spigot.skyblock.plugin.objects.Actions

val PlayerInteractEvent.isRightClick
    get() = action in Actions.anyRightClick

val PlayerInteractEvent.isRightClickAir
    get() = action == Action.RIGHT_CLICK_AIR

val PlayerInteractEvent.isRightClickBlock
    get() = action == Action.RIGHT_CLICK_BLOCK

val PlayerInteractEvent.isLeftClick
    get() = action in Actions.anyLeftClick

val PlayerInteractEvent.isLeftClickAir
    get() = action == Action.LEFT_CLICK_AIR

val PlayerInteractEvent.isLeftClickBlock
    get() = action == Action.LEFT_CLICK_BLOCK

val PlayerInteractEvent.isPhysical
    get() = action == Action.PHYSICAL
