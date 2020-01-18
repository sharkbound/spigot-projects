package sharkbound.spigot.skyblock

import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import sharkbound.spigot.skyblock.plugin.SkyBlock

lateinit var plugin: SkyBlock

inline fun usePlugin(func: SkyBlock.() -> Unit) {
    plugin.func()
}

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

inline fun usePluginManager(func: PluginManager.() -> Unit) {
    pluginManager.func()
}