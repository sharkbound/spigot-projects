package sharkbound.spigot.skyblock.plugin.utils

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.bukkit.BukkitWorld
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.PluginManager
import org.bukkit.util.Vector
import sharkbound.commonutils.extensions.len
import sharkbound.commonutils.extensions.use
import sharkbound.spigot.skyblock.plugin.*
import sharkbound.spigot.skyblock.plugin.commands.*
import sharkbound.spigot.skyblock.plugin.enums.CoordPosition
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.extensions.skyBlockWorldName
import sharkbound.spigot.skyblock.plugin.generators.PlayerSkyIslandGenerator
import sharkbound.spigot.skyblock.plugin.generators.VoidChunkGenerator
import sharkbound.spigot.skyblock.plugin.listeners.InventoryListener
import sharkbound.spigot.skyblock.plugin.listeners.WorldChangeListener
import java.io.File
import java.util.*

val allCommands = mutableListOf<CommandExecutor>()
val allEventListeners = mutableListOf<Listener>()

fun registerAllCommands() {
    allCommands.addAll(
        listOf(
            CommandSkyBlock(),
            CommandStop(),
            CommandDTP(),
            CommandListWorlds(),
            CommandTest()
        )
    )
}

fun registerAllEventListeners() {
    allEventListeners.addAll(
        listOf(
            WorldChangeListener(),
            InventoryListener()
        )
    )
}

fun colorFormat(message: String, char: Char = '&') =
    ChatColor.translateAlternateColorCodes(char, message)

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
    Bukkit.createWorld(WorldCreator(name).generator(VoidChunkGenerator()))

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
    getWorld(name) != null

inline fun usePlugin(func: SkyBlock.() -> Unit) {
    skyBlockInstance.func()
}

inline fun usePluginManager(func: PluginManager.() -> Unit) {
    pluginManager.func()
}

fun getWorld(name: String): World? =
    Bukkit.getWorld(name)

fun getWorld(id: UUID): World? =
    Bukkit.getWorld(id)

@Suppress("DEPRECATION")
fun createSkyBlockWorld(player: Player): World {
    skyIslandGenerationQueue[player.uniqueId] =
        PlayerSkyIslandGenerator(player)
    return createVoidWorld(player.skyBlockWorldName)
}

fun cannotBeCalledFromConsole(): Boolean {
    println("this command cannot be called from console")
    return false
}

fun Array<out String>.wrongArgsLength(required: Int, msg: String? = null, usage: String? = null): Boolean {
    when {
        msg != null -> println("missing required arguments, required: $required, actual: $len, message: $msg")
        usage != null -> println("missing required arguments, required: $required, actual: $len, usage: $usage")
        else -> println("missing required arguments, required: $required, actual: $len")
    }
    return false
}

fun Array<out String>.wrongArgsLength(
    caller: Player,
    required: Int,
    msg: String? = null,
    usage: String? = null
): Boolean {
    when {
        msg != null -> caller.send("&4missing required arguments, required: $required, actual: $len, message: $msg")
        usage != null -> caller.send("&4missing required arguments, required: $required, actual: $len, usage: $usage")
        else -> caller.send("&4missing required arguments, required: $required, actual: $len")
    }
    return false
}

fun vect(x: Int = 0, y: Int = 0, z: Int = 0) =
    Vector(x, y, z)

fun vect(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) =
    Vector(x, y, z)

fun vect(value: Int, mode: CoordPosition) =
    when (mode) {
        CoordPosition.X -> vect(value, 0, 0)
        CoordPosition.Y -> vect(0, value, 0)
        CoordPosition.Z -> vect(0, 0, value)
    }

fun vect(value: Double, mode: CoordPosition) =
    when (mode) {
        CoordPosition.X -> vect(value, 0.0, 0.0)
        CoordPosition.Y -> vect(0.0, value, 0.0)
        CoordPosition.Z -> vect(0.0, 0.0, value)
    }

@Suppress("DEPRECATION")
inline fun World.worldEditSession(handler: (EditSession) -> Unit) =
    EditSession(BukkitWorld(this), WorldEditConstants.MAX_WORLD_EDIT_BLOCKS).apply(handler)
