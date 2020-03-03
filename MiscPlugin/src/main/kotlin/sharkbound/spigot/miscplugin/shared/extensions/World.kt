package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import sharkbound.spigot.miscplugin.shared.utils.vector

inline fun <reified T : Entity> World.spawnAs(location: Location): T? =
    spawn(location, T::class.java)

inline fun <reified T : Entity> World.spawnEntityAs(location: Location, entityType: EntityType): T? =
    spawnEntity(location, entityType) as? T

fun World.blocksInRadius(center: Location, radius: Int): Sequence<Block> =
    sequence {
        yield(getBlockAt(center.clone()))
        yieldAll(center.locationsInRadius(radius).map { it.block })
    }