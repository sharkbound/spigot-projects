package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.miscplugin.shared.colored
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun Player.send(obj: Any?, altColorChar: Char = '&') {
    sendMessage(colored(obj.toString(), altColorChar))
}

val Player.direction
    get() = location.direction

val defaultTransparent = setOf<Material>()
const val defaultMaxDistance = 9999

fun Player.targetBlock(maxDistance: Int = defaultMaxDistance) =
    getTargetBlock(null, maxDistance)

fun Player.target(maxDistance: Int = defaultMaxDistance) =
    targetBlock(maxDistance).location

@ExperimentalContracts
fun CommandSender.isPlayer(): Boolean {
    contract {
        returns(true) implies (this@isPlayer is Player)
    }
    return this is Player
}

@ExperimentalContracts
fun CommandSender.isConsole(): Boolean {
    contract {
        returns(true) implies (this@isConsole is ConsoleCommandSender)
    }
    return this is ConsoleCommandSender
}

@ExperimentalContracts
fun CommandSender.isNotPlayer(): Boolean {
    contract {
        returns(true) implies (this@isNotPlayer !is Player)
    }
    return this !is Player
}

infix fun Player.replaceHeldItem(newItemStack: ItemStack) {
    inventory.setItem(inventory.heldItemSlot, newItemStack)
}