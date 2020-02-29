package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType

inline fun <reified T : Entity> World.spawnAs(location: Location): T? =
    spawn(location, T::class.java)

inline fun <reified T : Entity> World.spawnEntityAs(location: Location, entityType: EntityType): T? =
    spawnEntity(location, entityType) as? T