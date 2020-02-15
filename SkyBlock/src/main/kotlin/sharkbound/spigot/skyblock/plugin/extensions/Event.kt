package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

val PlayerItemHeldEvent.item: ItemStack?
    get() = player.inventory[newSlot]

val PlayerItemHeldEvent.lastItem: ItemStack?
    get() = player.inventory[previousSlot]

val PlayerItemHeldEvent.hasLastItem get() = lastItem != null
val PlayerItemHeldEvent.hasItem get() = item != null

fun PlayerEvent.send(obj: Any?, altColorChar: Char = '&') {
    player.send(obj, altColorChar)
}

val PlayerEvent.playerInv: Inventory
    get() = player.inventory