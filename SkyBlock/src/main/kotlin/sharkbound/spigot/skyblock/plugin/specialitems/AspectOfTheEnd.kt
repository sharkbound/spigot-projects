package sharkbound.spigot.skyblock.plugin.specialitems

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemFlags
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.colorAll
import sharkbound.spigot.skyblock.plugin.utils.vectorOfZeros

object AspectOfTheEnd {
    val color = "&5".colored()
    val itemName = "${color}Aspect Of The End".colored()
    val itemLore = colorAll("&r${Text.TIER}: &5EPIC")
    val minTpRange = 5.0
    val shopItemName
        get() = "$itemName &e(${Config.aspectOfTheEndCost} ${Config.tokenName})".colored()

    fun finalItem() =
        buildItem {
            material(Material.DIAMOND_SWORD)
            specialItemFlag(SpecialItemFlags.AspectOfTheEnd)
            displayName(itemName)
            lore(itemLore)
        }

    private fun Player.resetVelocity() {
        player.velocity = vectorOfZeros()
    }

    fun activate(player: Player) {
        if (teleport(player)) {
            player.playSound(player.location, Sound.ENDERMAN_TELEPORT, .5f, 1f)
        }
    }

    fun Player.itemError(msg: String, errorColor: String = "&3"): Boolean {
        player.send("$errorColor[&r${itemName}$errorColor] $msg")
        return false
    }

    private fun teleport(player: Player): Boolean {
        val horizonalOffset = 1.35
        val verticalOffset = 2

        player.apply {
            if (target(Config.aspectOfTheEndRange).location.distance(player.location) <= minTpRange) {
                return player.itemError("that block is too close to teleport to")
            }
        }

        player.lastTwoTargets(Config.aspectOfTheEndRange, includeTransparentBlocks = true)?.also {
            if (it.firstIsAir) {
                player.teleport(it.firstPos.cloneApply { yaw = player.yaw; pitch = player.pitch })
                return true
            }
            if (it.firstIsBlock) {
                val location = when (it.face) {
                    BlockFace.UP -> it.firstPos.cloneApply { y += 1 }
                    BlockFace.DOWN -> it.firstPos.cloneApply { y -= 2 }
                    BlockFace.NORTH -> it.firstPos.cloneApply { z -= horizonalOffset }
                    BlockFace.SOUTH -> it.firstPos.cloneApply { z += horizonalOffset }
                    BlockFace.EAST -> it.firstPos.cloneApply { x += horizonalOffset }
                    BlockFace.WEST -> it.firstPos.cloneApply { x -= horizonalOffset }
                    else -> it.firstPos.cloneApply { y += verticalOffset }
                }

                player.resetVelocity()
                player.teleport(location.cloneApply {
                    yaw = player.yaw
                    pitch = player.pitch
                })

                return true
            }
        }
        return false
    }
}

