package sharkbound.spigot.skyblock.plugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.plugin.utils.registerAllCommands
import sharkbound.spigot.skyblock.plugin.utils.registerAllEventListeners

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        skyBlockInstance = this

        registerAllCommands()
        registerAllEventListeners()

        saveDefaultConfig()

        println("SkyBlock loaded!, CWD: $cwd")
    }
}