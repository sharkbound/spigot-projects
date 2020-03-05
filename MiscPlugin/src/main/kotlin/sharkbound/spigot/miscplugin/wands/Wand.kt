package sharkbound.spigot.miscplugin.wands

import org.bukkit.inventory.ItemStack
import sharkbound.spigot.miscplugin.shared.extensions.modifyNBT

interface Wand {
    val nbtId: String
    fun create(): ItemStack

    fun ItemStack.applyNBT() =
        modifyNBT {
            setString("wandtype", nbtId)
        }
}