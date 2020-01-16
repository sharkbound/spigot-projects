package sharkbound.spigot.skyblock.plugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.plugin.commands.HelloWorld

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        HelloWorld(this)
        println("SkyBlock loaded!")
    }
}