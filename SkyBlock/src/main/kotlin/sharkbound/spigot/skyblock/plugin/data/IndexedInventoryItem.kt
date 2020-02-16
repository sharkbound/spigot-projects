package sharkbound.spigot.skyblock.plugin.data

import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.remaining

data class IndexedInventoryItem(val slot: Int, val item: ItemStack) {
    /**
     * how much more can be added to the stack?
     */
    val remaining = item.remaining
}