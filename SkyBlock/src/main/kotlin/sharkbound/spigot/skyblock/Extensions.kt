package sharkbound.spigot.skyblock

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.io.File


fun CommandExecutor.register(name: String) {
    plugin.getCommand(name).executor = this
}

fun <T : Listener> T.register() {
    pluginManager.registerEvents(this, plugin)
}

fun Player.target(distance: Int = 5000, materials: Set<Material> = setOf(Material.AIR)) =
    getTargetBlock(materials, distance)

val Player.lookLocation get() = target().location

val Material.idByte get() = id.toByte()

inline fun <reified T> World.spawnEntityCast(location: Location, entityType: EntityType) =
    spawnEntity(location, entityType) as? T

fun Player.sendColored(message: String, char: Char = '&') = colorFormat(message, char)

fun deleteWorld(path: File): Boolean {
    if (path.exists()) {
        path.listFiles()?.let { files ->
            for (i in files.indices) {
                if (files[i].isDirectory) {
                    deleteWorld(files[i]!!)
                } else {
                    files[i]?.delete()
                }
            }
        }
    }
    return path.delete()
}