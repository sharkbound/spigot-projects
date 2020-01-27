package sharkbound.spigot.skyblock.plugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.cwd
import sharkbound.spigot.skyblock.skyBlockInstance
import sharkbound.spigot.skyblock.registerAllCommands
import sharkbound.spigot.skyblock.registerAllEventListeners

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        skyBlockInstance = this

        registerAllCommands()
        registerAllEventListeners()

        println("SkyBlock loaded!, CWD: $cwd")
    }
}