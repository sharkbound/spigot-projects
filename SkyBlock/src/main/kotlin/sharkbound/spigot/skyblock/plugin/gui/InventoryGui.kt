package sharkbound.spigot.skyblock.plugin.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import sharkbound.spigot.skyblock.plugin.customitems.CustomItemBase
import sharkbound.spigot.skyblock.plugin.extensions.cancel
import sharkbound.spigot.skyblock.plugin.extensions.name
import sharkbound.spigot.skyblock.plugin.extensions.normalized

open class InventoryGui(val name: String, val rows: Int) {
    val elements = mutableListOf<GuiElement>()
    val elementNames get() = elements.asSequence().map { it.item.name }.toMutableSet()
    val normalizedElementNames get() = elements.asSequence().map { it.normalizedName }.toMutableSet()
    val normalizedName = name.normalized

    private fun createInventory() =
        Bukkit.createInventory(null, rows * 9, name)

    fun addElement(
        x: Int,
        y: Int,
        item: CustomItemBase
    ): GuiElement =
        GuiElement(x, y, item).also { elements.add(it) }

    open fun addItemsToInv(inventory: Inventory) {
        elements.forEach {
            inventory.setItem(it.slot, it.item)
        }
    }

    open fun prepareInventory(player: Player) =
        createInventory().also { addItemsToInv(it) }

    open fun show(player: Player) {
        player.openInventory(prepareInventory(player))
    }

    open fun handleClickedEvent(e: InventoryClickEvent): Boolean {
        val itemName = e.currentItem?.itemMeta?.displayName
        if (itemName == null || itemName !in elementNames) {
            return false
        }

        e.cancel()
        e.whoClicked?.let {
            if (it !is Player) {
                return false
            }
            val clickedElement = get(itemName) ?: return false
            clicked(it, clickedElement, clickedElement.normalizedName, clickedElement.item.name, e)
        }

        return true
    }

    open fun clicked(
        player: Player,
        element: GuiElement,
        normalizedName: String,
        name: String,
        e: InventoryClickEvent
    ) {
        throw RuntimeException("unimplemented clicked() handler for menu ${javaClass.name}")
    }

    override fun equals(other: Any?): Boolean {
        return other is String && other.normalized == name.normalized
    }

    operator fun get(elementName: String) =
        elements.firstOrNull { it.normalizedName == elementName.normalized }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + rows
        result = 31 * result + normalizedName.hashCode()
        return result
    }
}

val allGui = mutableMapOf<String, InventoryGui>()

fun addInventoryGui(vararg gui: InventoryGui) {
    gui.forEach {
        allGui[it.normalizedName] = it
    }
}


