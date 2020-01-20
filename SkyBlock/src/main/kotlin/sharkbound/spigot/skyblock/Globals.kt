package sharkbound.spigot.skyblock

import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import sharkbound.spigot.skyblock.plugin.SkyBlock
import java.util.*

lateinit var plugin: SkyBlock

inline fun usePlugin(func: SkyBlock.() -> Unit) {
    plugin.func()
}

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

inline fun usePluginManager(func: PluginManager.() -> Unit) {
    pluginManager.func()
}

val overworld get() = Bukkit.getWorld("world")!!

fun getWorld(name: String) = Bukkit.getWorld(name)

fun getWorld(id: UUID) = Bukkit.getWorld(id)

val allWorlds get() = Bukkit.getWorlds()
