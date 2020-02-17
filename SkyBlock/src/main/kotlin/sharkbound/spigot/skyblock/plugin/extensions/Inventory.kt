package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.PLAYER_INV_SIZE
import sharkbound.spigot.skyblock.plugin.data.IndexedInventoryItem

operator fun Inventory.get(slot: Int): ItemStack? =
    getItem(slot)

operator fun Inventory.get(x: Int, y: Int): ItemStack? =
    getItem(y * 9 + x)

operator fun Inventory.set(slot: Int, value: ItemStack) =
    setItem(slot, value)

operator fun Inventory.set(x: Int, y: Int, value: ItemStack) =
    setItem(y * 9 + x, value)

val Inventory.items: Sequence<ItemStack>
    get() = asSequence().filterNotNull()

val Inventory.hasFreeSlot: Boolean
    get() = firstEmpty() != -1

inline fun Inventory.removeWhereIndexed(
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
    removeWhereIndexed(limit) { _, item -> predicate(item) }
}

val Inventory.indexedNullable
    get() = asSequence().mapIndexed { slot, item ->
        if (item != null) IndexedInventoryItem(slot, item) else null
    }

val Inventory.indexed
    get() = indexedNullable.filterNotNull()

fun Inventory.setNull(index: Int) {
    setItem(index, null)
}
