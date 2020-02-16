package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.entity.Player

val Player.hasFreeInvSlot
    get() = inventory.firstEmpty() != -1

inline infix fun <R> Player.closeInventoryAfter(block: (Player) -> R) =
    block(this).also {
        closeInventory()
    }

