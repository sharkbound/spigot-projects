package sharkbound.spigot.miscplugin.utils

import org.bukkit.inventory.ItemStack
import sharkbound.spigot.miscplugin.shared.extensions.nbt

object WandUtil {
    val id = "wandtype"
    fun idFrom(itemStack: ItemStack) =
        itemStack.nbt.getString(id) ?: ""
}