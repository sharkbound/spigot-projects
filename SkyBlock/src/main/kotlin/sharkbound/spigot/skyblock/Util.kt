package sharkbound.spigot.skyblock

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.WorldCreator
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

fun createVoidWorld(name: String) =
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

fun createWorld(name: String, chunkGenerator: ChunkGenerator) =
    Bukkit.createWorld(WorldCreator(name).generator(chunkGenerator))

fun worldExists(name: String) =
    getWorld(name) != null

inline fun usePlugin(func: SkyBlock.() -> Unit) {
    plugin.func()
}

inline fun usePluginManager(func: PluginManager.() -> Unit) {
    pluginManager.func()
}

fun getWorld(name: String) = Bukkit.getWorld(name)
fun getWorld(id: UUID) = Bukkit.getWorld(id)

fun createSkyBlockWorld(player: Player) = createVoidWorld(player.skyBlockWorldName)

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