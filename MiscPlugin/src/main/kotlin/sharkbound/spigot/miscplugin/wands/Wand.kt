package sharkbound.spigot.miscplugin.wands

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.miscplugin.shared.builders.ItemBuilder

interface Wand {
    val nbtId: String
    fun create(): ItemStack

    fun ItemBuilder.setWandData() {
        enchant(Enchantment.PROTECTION_FIRE, 1)
        hideEnchants()
        nbt {
            setString("wandtype", nbtId)
        }
    }
}