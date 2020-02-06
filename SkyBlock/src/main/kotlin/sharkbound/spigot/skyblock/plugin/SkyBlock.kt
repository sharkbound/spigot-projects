package sharkbound.spigot.skyblock.plugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.plugin.database.DB
import sharkbound.spigot.skyblock.plugin.utils.registerAllCommands
import sharkbound.spigot.skyblock.plugin.utils.registerAllEventListeners

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        skyBlockInstance = this
        DB.init()

        registerAllCommands()
        registerAllEventListeners()

        saveDefaultConfig()

        println("SkyBlock loaded!")
    }
}