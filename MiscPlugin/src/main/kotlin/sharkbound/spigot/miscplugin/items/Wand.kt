package sharkbound.spigot.miscplugin.items

import org.bukkit.inventory.ItemStack

interface Wand {
    val nbtId: String
    fun create(): ItemStack
}