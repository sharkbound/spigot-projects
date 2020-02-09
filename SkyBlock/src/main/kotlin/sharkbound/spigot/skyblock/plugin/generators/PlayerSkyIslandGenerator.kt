package sharkbound.spigot.skyblock.plugin.generators

import com.sk89q.worldedit.Vector
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.Locations
import sharkbound.spigot.skyblock.plugin.objects.Schematics
import sharkbound.spigot.skyblock.plugin.utils.worldEditSession

data class PlayerSkyIslandGenerator(val player: Player) {
    private val world get() = player.world

    @Suppress("DEPRECATION")
    fun generate() {
        world.worldEditSession {
            Schematics
                .skyIslandSchematic.place(it, Vector(0.0, Locations.SKY_ISLAND_SCHEMATIC_Y, 0.0), false)
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
                    displayName = Config.cowSpawnEggName.colored()
                }
            },
            ItemStack(Material.SEEDS)
        )

    private fun addChestItems() {
        try {
            for (chunk in world.loadedChunks) {
                for (entity in chunk.tileEntities) {
                    if (entity.isChest) {
                        entity.asChest!!.inventory.addItem(*items())
                        return
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}