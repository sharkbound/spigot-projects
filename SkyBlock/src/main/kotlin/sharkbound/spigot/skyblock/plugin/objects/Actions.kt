package sharkbound.spigot.skyblock.plugin.objects

import org.bukkit.event.block.Action

object Actions {
    val anyRightClick = setOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR)
    val anyLeftClick = setOf(Action.LEFT_CLICK_BLOCK, Action.LEFT_CLICK_AIR)
}