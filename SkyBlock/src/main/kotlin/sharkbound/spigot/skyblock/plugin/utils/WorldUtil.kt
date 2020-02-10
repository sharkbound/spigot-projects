package sharkbound.spigot.skyblock.plugin.utils

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.entity.Player
import org.bukkit.generator.ChunkGenerator
import sharkbound.commonutils.extensions.use
import sharkbound.spigot.skyblock.plugin.extensions.div
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorldName
import sharkbound.spigot.skyblock.plugin.generators.PlayerSkyIslandGenerator
import sharkbound.spigot.skyblock.plugin.generators.VoidChunkGenerator
import sharkbound.spigot.skyblock.plugin.objects.FilePaths
import sharkbound.spigot.skyblock.plugin.skyIslandGenerationQueue
import java.io.File
import java.util.*

fun deleteWorld(worldName: String): Boolean {
    getWorld(worldName) use {
        Bukkit.unloadWorld(this, false)
        try {
            deleteWorld(worldFolder)
        } catch (e: Exception) {
            println("failed to delete world: $name")
            e.printStackTrace()
        }
        return true
    }
    return false
}

fun createVoidWorld(name: String): World =
    Bukkit.createWorld(
        WorldCreator(name)
            .generator(VoidChunkGenerator())
    )

fun deleteWorld(path: File): Boolean {
    if (path.exists()) {
        path.listFiles()?.let {
            for (file in it) {
                if (file.isDirectory) {
                    deleteWorld(file)
                } else {
                    file.delete()
                }
            }
        }
    }
    return path.delete()
}

fun createWorld(name: String, chunkGenerator: ChunkGenerator): World =
    Bukkit.createWorld(WorldCreator(name).generator(chunkGenerator))

fun worldExists(name: String) =
    Bukkit.getWorld(name) != null || FilePaths.worldFolder.toFile().div(name)
        .let { it.isDirectory && it.div("playerdata").isDirectory }

fun getWorld(name: String): World? {
    // note of the createWorld here, bukkit's getWorld() only get LOADED worlds
    // so what i did was to check if the world's folder exists
    // then load it using createWorld, createWorld loads a world if it exists in the files, but creates it if it does not
    return if (worldExists(name)) Bukkit.createWorld(WorldCreator(name)) else null
}

fun getWorld(id: UUID): World? =
    Bukkit.getWorld(id)

@Suppress("DEPRECATION")
fun createSkyBlockWorld(player: Player): World {
    skyIslandGenerationQueue[player.uniqueId] =
        PlayerSkyIslandGenerator(player)
    return createVoidWorld(player.skyBlockWorldName)
}