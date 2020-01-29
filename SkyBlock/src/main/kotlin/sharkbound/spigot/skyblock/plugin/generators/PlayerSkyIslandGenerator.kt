package sharkbound.spigot.skyblock.plugin.generators

import com.sk89q.worldedit.Vector
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.ConfigKeys
import sharkbound.spigot.skyblock.plugin.Coords
import sharkbound.spigot.skyblock.plugin.Schematics
import sharkbound.spigot.skyblock.plugin.cfg
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.utils.worldEditSession

data class PlayerSkyIslandGenerator(val player: Player) {
    val world get() = player.world

    @Suppress("DEPRECATION")
    fun generate() {
        world.worldEditSession {
            Schematics.skyIslandSchematic.place(it, Vector(0.0, Coords.SKY_ISLAND_SCHEMATIC_Y, 0.0), false)
        }
        addChestItems()
    }

    @Suppress("DEPRECATION")
    private fun items() =
        arrayOf(
            ItemStack(Material.GOLD_SWORD),
            ItemStack(Material.LEATHER_HELMET).enchant(Enchantment.PROTECTION_FIRE),
            ItemStack(Material.LEATHER_CHESTPLATE).enchant(Enchantment.PROTECTION_FIRE),
            ItemStack(Material.LEATHER_LEGGINGS).enchant(Enchantment.PROTECTION_FIRE),
            ItemStack(Material.LEATHER_BOOTS).enchant(Enchantment.PROTECTION_FIRE),
            ItemStack(Material.LAVA_BUCKET),
            ItemStack(Material.ICE, 2),
            ItemStack(Material.LOG, 15),
            ItemStack(Material.MONSTER_EGG).apply {
                durability = EntityType.COW.typeId
                modifyMeta {
                    displayName = cfg.getString(ConfigKeys.cowSpawnEggName).colorFormat()
                }

            },
            ItemStack(Material.SEEDS)
        )

    private fun addChestItems() {
        try {
            for (chunk in world.loadedChunks) {
                for (entity in chunk.tileEntities) {
                    if (entity.type == Material.CHEST) {
                        (entity.block.state as Chest).inventory.apply {
                            addItem(*items())
                        }
                        //                  we found the chest, so return and exit the loop
                        return
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}