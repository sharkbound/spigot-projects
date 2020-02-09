package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.RE_REMOVE_NON_ALPHA
import sharkbound.spigot.skyblock.plugin.utils.colored
import sharkbound.spigot.skyblock.plugin.utils.getWorld
import java.util.*

fun Player.target(
    distance: Int = 5000, materials: Set<Material> = setOf(
        Material.AIR
    )
) =
    getTargetBlock(materials, distance)

val Player.lookLocation get() = target().location
fun Player.send(message: String, char: Char = '&') =
    sendMessage(colored(message, char))

val Player.skyBlockWorldName get() = "skyblock_${RE_REMOVE_NON_ALPHA.replace(name, "")}"
val Player.skyBlockWorld: World? get() = getWorld(skyBlockWorldName)

val Player.strId
    get() = id.toString()

val Player.id: UUID
    get() = uniqueId

inline infix fun <R> Player.closeInventoryAfter(block: (Player) -> R) =
    block(this).also {
        closeInventory()
    }

val Player.hasFreeInvSlot
    get() = inventory.firstEmpty() != -1

val Player.lookDirection
    get() = location.direction

val Player.yaw
    get() = location.yaw

val Player.pitch
    get() = location.pitch
