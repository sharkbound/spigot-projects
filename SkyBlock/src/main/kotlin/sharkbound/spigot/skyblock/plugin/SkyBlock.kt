package sharkbound.spigot.skyblock.plugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.plugin
import sharkbound.spigot.skyblock.registerAllCommands
import sharkbound.spigot.skyblock.registerAllEventListeners

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        plugin = this

        registerAllCommands()
        registerAllEventListeners()
        
        println("SkyBlock loaded!")
    }
}