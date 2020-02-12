package sharkbound.spigot.skyblock.plugin.specialitems

import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.data.YamlCooldownBase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.ItemTier
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemFlags
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.colorAll
import sharkbound.spigot.skyblock.plugin.utils.vect
import sharkbound.spigot.skyblock.plugin.utils.vectorOfZeros

private object AspectOfTheEndCooldown :
    YamlCooldownBase("aspect_of_the_end_cooldowns.yml", Config.aspectOfTheEndCooldown)

object AspectOfTheEnd {
    val color = "&5".colored()
    val itemName = "${color}Aspect Of The End".colored()
    val itemLore = colorAll("&r${Text.TIER}: ${ItemTier.EPIC}")
    val minTpRange = 5.0
    val shopItemName
        get() = "$itemName &e(${Config.aspectOfTheEndCost} ${Config.currencyName})".colored()

    fun finalItem() =
        buildItem {
            material(Material.DIAMOND_SWORD)
            specialItemFlag(SpecialItemFlags.AspectOfTheEnd)
            displayName(itemName)
            lore(itemLore)
        }


    fun activate(player: Player) {
        if (AspectOfTheEndCooldown.onCooldown(player.id)) {
            val remainingCooldown = AspectOfTheEndCooldown.remainingCooldownFormatted(player.id)
            player.itemError("you must wait $remainingCooldown seconds to use this item again")
            return
        }

        if (teleport(player)) {
            player.playSound(player.location, Sound.ENDERMAN_TELEPORT, .5f, 1f)
            AspectOfTheEndCooldown.update(player.id)
        }
    }

    private fun Player.itemError(msg: String, errorColor: String = "&3"): Boolean {
        player.send("$errorColor[&r${itemName}$errorColor] $msg")
        return false
    }

//    val horizonalOffset = 1.2
//    val verticalOffset = 1

    private fun teleport(player: Player): Boolean {
        player.apply {
            if (target(Config.aspectOfTheEndRange).location.distance(player.location) <= minTpRange) {
                return player.itemError("that block is too close to teleport to")
            }
        }

        player.lastTwoTargets(Config.aspectOfTheEndRange, includeTransparentBlocks = true)?.also {
            if (it.firstIsAir) {
                player.teleport(it.firstPos.keepYawPitch(player.location))
                return true
            }

            if (it.firstIsBlock) {
                val posCorrection = .5
                val offset = when (it.face) {
                    // all these vectors are corrections when teleporting
                    // the block's position is slightly offset when a player is teleported to it
                    // the most of the .5's are there to correct this
                    BlockFace.UP -> vect(y = 1.0, x = posCorrection, z = posCorrection)
                    BlockFace.DOWN -> vect(y = -2.0, x = posCorrection, z = posCorrection)
                    BlockFace.NORTH -> vect(z = -posCorrection, x = posCorrection)
                    BlockFace.SOUTH -> vect(z = 1.5, x = posCorrection)
                    BlockFace.EAST -> vect(x = 1.5, z = posCorrection)
                    BlockFace.WEST -> vect(x = -posCorrection, z = posCorrection)
                    else -> vectorOfZeros()
                }

                val offsetLocation = it.firstPos.add(offset)

                if (Config.aspectOfTheEndWallYAutoCorrect > 0) {
                    for (i in 0..Config.aspectOfTheEndWallYAutoCorrect) {
                        val block = player.world.getBlockAt(offsetLocation.cloneApply { y -= i })
                        if (block.type !in TargetGlobals.invalidTargets) {
                            offsetLocation.apply { y = block.y + 1.0 }
                            break
                        }
                    }
                }

                if (player.world.typeAt(offsetLocation.cloneApply { y += 1 }) != Material.AIR) {
                    return player.itemError("teleporting there is not safe!")
                }

                player.teleport(offsetLocation.cloneApply {
                    yaw = player.yaw
                    pitch = player.pitch
                })

                return true
            }
        }
        return false
    }
}

