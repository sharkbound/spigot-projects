package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity

inline fun <reified T : Entity> World.spawnAs(location: Location): T =
    spawn(location, T::class.java)