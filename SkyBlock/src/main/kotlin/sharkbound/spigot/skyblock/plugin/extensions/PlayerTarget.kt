package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.len

data class LastTwoTarget(val first: Block, val second: Block) {
    val face: BlockFace = first.getFace(second)

    val firstType = first.type
    val secondType = second.type

    val firstIsAir = firstType == Material.AIR
    val secondIsAir = secondType == Material.AIR

    val firstIsLiquid = firstType in TargetGlobals.liquids
    val secondIsLiquid = secondType in TargetGlobals.liquids

    val firstIsBlock = firstType != Material.AIR && !firstIsLiquid
    val secondIsBlock = secondType != Material.AIR && !secondIsLiquid

    val firstIsOccluding = firstType.isOccluding
    val secondIsOccluding = secondType.isOccluding

    val firstX = first.location.x
    val firstY = first.location.y
    val firstZ = first.location.z

    val secondX = second.location.x
    val secondY = second.location.y
    val secondZ = second.location.z

    val firstPos = first.location
    val secondPos = second.location

}

object TargetGlobals {
    const val DEFAULT_TARGET_RANGE = Int.MAX_VALUE
    val defaultTransparent = setOf(Material.AIR, Material.WATER, Material.LAVA)
    val liquids = setOf(Material.WATER, Material.STATIONARY_WATER, Material.LAVA, Material.STATIONARY_LAVA)

}

fun Player.target(
    maxDistance: Int = TargetGlobals.DEFAULT_TARGET_RANGE,
    transparent: Set<Material> = TargetGlobals.defaultTransparent
) =
    getTargetBlock(transparent, maxDistance)

fun Player.lastTwoTargets(
    maxDistance: Int = TargetGlobals.DEFAULT_TARGET_RANGE,
    transparent: Set<Material> = TargetGlobals.defaultTransparent,
    includeTransparentBlocks: Boolean = false
): LastTwoTarget? {
    val blocks = getLastTwoTargetBlocks(transparent, maxDistance)
    if (blocks.len != 2 || (!includeTransparentBlocks && !blocks[1].type.isOccluding))
        return null
    return LastTwoTarget(blocks[1], blocks[0])

}

fun Player.lookLocation(
    maxDistance: Int = TargetGlobals.DEFAULT_TARGET_RANGE,
    transparent: Set<Material> = TargetGlobals.defaultTransparent
) =
    target(maxDistance, transparent).location

val Player.lookDirection
    get() = location.direction


fun Player.lookFace(
    maxDistance: Int = TargetGlobals.DEFAULT_TARGET_RANGE,
    transparent: Set<Material> = TargetGlobals.defaultTransparent,
    includeTransparentBlocks: Boolean = false
): BlockFace? =
    lastTwoTargets(maxDistance, transparent, includeTransparentBlocks)?.face



