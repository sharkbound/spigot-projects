package sharkbound.spigot.skyblock.plugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.cfg
import sharkbound.spigot.skyblock.cwd
import sharkbound.spigot.skyblock.skyBlockInstance
import sharkbound.spigot.skyblock.utils.registerAllCommands
import sharkbound.spigot.skyblock.utils.registerAllEventListeners

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        skyBlockInstance = this

        registerAllCommands()
        registerAllEventListeners()

        saveDefaultConfig()

        println("SkyBlock loaded!, CWD: $cwd")
        println(cfg.getString("sky_island_schematic"))
    }
}