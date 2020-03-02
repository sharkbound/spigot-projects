package sharkbound.spigot.miscplugin

import org.bukkit.plugin.java.JavaPlugin
import sharkbound.spigot.miscplugin.commands.*
import sharkbound.spigot.miscplugin.items.LevitationListener
import sharkbound.spigot.miscplugin.listeners.MobListener
import sharkbound.spigot.miscplugin.listeners.PlayerListener
import sharkbound.spigot.miscplugin.shared.instance

class MiscPlugin : JavaPlugin() {
    override fun onEnable() {
        instance = this

        load()
    }

    private fun load() {
        CommandEverBlaze
        CommandStop
        CommandShulkerWand
        CommandShulkerPortal
        CommandPhantomPortal
        CommandWand
        CommandPotionRain
        LevitationListener
        PlayerListener
        MobListener
    }
}
