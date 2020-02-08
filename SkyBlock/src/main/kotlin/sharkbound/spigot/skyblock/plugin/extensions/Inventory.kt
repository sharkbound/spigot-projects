package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

operator fun Inventory.get(index: Int): ItemStack? =
    getItem(index)