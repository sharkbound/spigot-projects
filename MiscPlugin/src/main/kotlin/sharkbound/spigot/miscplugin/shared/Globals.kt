package sharkbound.spigot.miscplugin.shared

import org.bukkit.Bukkit
import sharkbound.spigot.miscplugin.MiscPlugin

lateinit var instance: MiscPlugin

val manager
    get() = Bukkit.getPluginManager()

val server
    get() = Bukkit.getServer()
