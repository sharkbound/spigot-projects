package sharkbound.spigot.miscplugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.miscplugin.commands.*
import sharkbound.spigot.miscplugin.listeners.MobListener
import sharkbound.spigot.miscplugin.listeners.PlayerListener
import sharkbound.spigot.miscplugin.shared.instance

class MiscPlugin : JavaPlugin() {
    override fun onEnable() {
        instance = this

        load()
    }

    private fun load() {
        CommandTest
        CommandStop
        CommandShulkerWand
        CommandShulkerPortal
        CommandPhantomPortal
        PlayerListener
        MobListener
    }
}
