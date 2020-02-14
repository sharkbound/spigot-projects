package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.PLAYER_INV_SIZE

val Player.hasFreeInvSlot
    get() = inventory.firstEmpty() != -1

inline infix fun <R> Player.closeInventoryAfter(block: (Player) -> R) =
    block(this).also {
        closeInventory()
    }

inline fun Inventory.removeWhereWithIndex(
    limit: Int = -1,
    predicate: (
        @ParameterName("i") Int,
        @ParameterName("item") ItemStack
    ) -> Boolean
) {
    var removed = limit
    for (i in 0 until PLAYER_INV_SIZE) {
        if (removed == 0) return

        getItem(i)?.let {
            if (predicate(i, it)) {
                setItem(i, null)
                removed -= 1
            }
        }
    }
}

inline fun Inventory.removeWhere(limit: Int = -1, predicate: (ItemStack) -> Boolean) {
    removeWhereWithIndex(limit) { _, item -> predicate(item) }
}