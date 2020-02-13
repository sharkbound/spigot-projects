package sharkbound.spigot.skyblock.plugin

import org.bukkit.command.CommandExecutor
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.skyblock.plugin.commands.*
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.gui.*
import sharkbound.spigot.skyblock.plugin.listeners.PlayerEvent
import sharkbound.spigot.skyblock.plugin.listeners.SkyBlockWorldChange
import sharkbound.spigot.skyblock.plugin.objects.FilePaths

class SkyBlock : JavaPlugin() {
    override fun onEnable() {
        skyBlockInstance = this
        SkyBlockDatabase.init()

        registerAllCommands()
        registerAllEventListeners()
        registerAllInventoryGui()

        saveDefaultConfig()
        println("SkyBlock loaded! ${FilePaths.worldFolder}")
    }
}

internal val allCommands = mutableListOf<CommandExecutor>()
internal val allEventListeners = mutableListOf<Listener>()

private fun registerAllCommands() {
    allCommands.addAll(
        listOf(
            CommandSkyBlock(),
            CommandStop(),
            CommandDTP(),
            CommandListWorlds(),
            CommandTest()
        )
    )
}

private fun registerAllEventListeners() {
    allEventListeners.addAll(
        listOf(
            SkyBlockWorldChange(),
            InventoryGuiClickListener(),
            PlayerEvent()
        )
    )
}


fun registerAllInventoryGui() {
    addInventoryGui(SkyIslandGui, ShopGui, MobileBankGui)
}