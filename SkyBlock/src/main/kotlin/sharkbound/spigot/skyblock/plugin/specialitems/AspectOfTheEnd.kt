package sharkbound.spigot.skyblock.plugin.specialitems

import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.colorAll

object AspectOfTheEnd {
    val color = "&5".colored()
    val itemName = "${color}Aspect Of The End".colored()
    val itemLore = colorAll("&r${Text.TIER}: &5EPIC")
    val shopItemName
        get() = "$itemName &e(${Config.aspectOfTheEndCost} ${Config.tokenName})".colored()

    fun activate(player: Player) {
        if (teleport(player)) {
            player.playSound(player.location, Sound.ENDERMAN_TELEPORT, .5f, 1f)
        }
    }

    private fun teleport(player: Player): Boolean {
        val horizonalOffset = 1.3
        val verticalOffset = 2
        val error = "&3"

        player.apply {
            if (target(Config.aspectOfTheEndRange).location.distance(player.location) <= 3.5) {
                player.send("$error[&r${player.itemInHand.name}$error] that block is too close to teleport to")
                return false
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

