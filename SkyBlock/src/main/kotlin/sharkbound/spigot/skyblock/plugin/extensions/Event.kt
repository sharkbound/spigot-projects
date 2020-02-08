package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

val PlayerItemHeldEvent.newItem: ItemStack?
    get() = player.inventory[newSlot]

val PlayerItemHeldEvent.lastItem: ItemStack?
    get() = player.inventory[previousSlot]

val PlayerItemHeldEvent.hasLastItem get() = lastItem != null
val PlayerItemHeldEvent.hasNewItem get() = newItem != null