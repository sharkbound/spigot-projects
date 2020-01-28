package sharkbound.spigot.skyblock.extensions

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.EntityType
import sharkbound.spigot.skyblock.utils.deleteWorld

fun World.setBlock(x: Int, y: Int, z: Int, new: Material) {
    getBlockAt(x, y, z).type = new
}

inline fun World.setBlock(x: Int, y: Int, z: Int, material: Block.() -> Material) {
    getBlockAt(x, y, z).apply {
        type = material()
    }
}

fun World.setBlock(loc: Location, new: Material) {
    getBlockAt(loc).type = new
}

inline fun World.setBlock(loc: Location, material: Block.() -> Material) {
    getBlockAt(loc).apply {
        type = material()
    }
}

fun World.delete() {
    deleteWorld(name)
}

inline fun <reified T> World.spawnEntityCast(location: Location, entityType: EntityType) =
    spawnEntity(location, entityType) as? T