package sharkbound.spigot.skyblock.plugin.data

import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.remaining

data class IndexedInventoryItem(val slot: Int, val item: ItemStack) {
    val remaining = item.remaining
}