package sharkbound.spigot.skyblock

import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.regions.CuboidRegion
import org.bukkit.*
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.PluginManager
import sharkbound.commonutils.extensions.len
import sharkbound.commonutils.extensions.use
import sharkbound.spigot.skyblock.plugin.SkyBlock
import sharkbound.spigot.skyblock.plugin.commands.CommandDTP
import sharkbound.spigot.skyblock.plugin.commands.CommandListWorlds
import sharkbound.spigot.skyblock.plugin.commands.CommandSkyBlock
import sharkbound.spigot.skyblock.plugin.commands.CommandStop
import sharkbound.spigot.skyblock.plugin.generators.VoidChunkGenerator
import sharkbound.spigot.skyblock.plugin.listeners.TntListener
import java.io.File
import java.lang.Exception
import java.util.*

val allCommands = mutableListOf<CommandExecutor>()
val allEventListeners = mutableListOf<Listener>()

fun registerAllCommands() {
    allCommands.addAll(
        listOf(
            CommandSkyBlock(),
            CommandStop(),
            CommandDTP(),
            CommandListWorlds()
        )
    )
}

fun registerAllEventListeners() {
    allEventListeners += TntListener()
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

fun getWorld(name: String): World? = Bukkit.getWorld(name)
fun getWorld(id: UUID): World? = Bukkit.getWorld(id)

@Suppress("DEPRECATION")
fun createSkyBlockWorld(player: Player): World {
    val world = createVoidWorld(player.skyBlockWorldName)
    println("pre-edit-session")
    worldEdit.createEditSession(player).also {
        println("pre-create-schem")
        val schem = CuboidClipboard.loadSchematic(File(cfg.getString(ConfigKeys.SKY_ISLAND_SCHEMATIC)))
        println("pre-place-schem, schem: $schem")
        schem.place(it, Vector(0.0, Coords.SKY_ISLAND_SCHEMATIC_Y, 0.0), false)
    }
    return world
}

fun cannotBeCalledFromConsole(): Boolean {
    println("this command cannot be called from console")
    return false;
}

fun Array<out String>.wrongArgsLength(required: Int, msg: String? = null, usage: String? = null): Boolean {
    when {
        msg != null -> println("missing required arguments, required: $required, actual: $len, message: $msg")
        usage != null -> println("missing required arguments, required: $required, actual: $len, usage: $usage")
        else -> println("missing required arguments, required: $required, actual: $len")
    }
    return false
}