package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Material
import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.colored

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
