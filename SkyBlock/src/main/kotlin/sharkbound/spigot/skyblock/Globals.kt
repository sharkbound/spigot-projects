package sharkbound.spigot.skyblock

import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import sharkbound.spigot.skyblock.plugin.SkyBlock

lateinit var plugin: SkyBlock

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

val overworld get() = Bukkit.getWorld("world")!!

val allWorlds get() = Bukkit.getWorlds()

val RE_REMOVE_NON_ALPHA = """[^\w]""".toRegex()