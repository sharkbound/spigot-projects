package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.RE_REMOVE_NON_ALPHA
import sharkbound.spigot.skyblock.plugin.utils.colored
import sharkbound.spigot.skyblock.plugin.utils.getWorld
import java.util.*

val defaultIgnored = setOf(Material.AIR, Material.WATER, Material.LAVA)
const val DEFAULT_TARGET_RANGE = Int.MAX_VALUE

fun Player.target(
    distance: Int = DEFAULT_TARGET_RANGE, materials: Set<Material> = defaultIgnored
) =
    getTargetBlock(materials, distance)

fun Player.lastTwoTarget(
    distance: Int = DEFAULT_TARGET_RANGE, materials: Set<Material> = defaultIgnored
): List<Block>? =
    getLastTwoTargetBlocks(materials, distance)

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
