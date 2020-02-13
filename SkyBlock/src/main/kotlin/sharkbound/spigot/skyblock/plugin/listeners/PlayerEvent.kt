package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.logger
import sharkbound.spigot.skyblock.plugin.objects.Actions
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.customitems.AspectOfTheEnd
import sharkbound.spigot.skyblock.plugin.customitems.EmberRod
import java.util.logging.Level

class PlayerEvent : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        SkyBlockDatabase.initPlayer(e.player)
        SkyBlockDatabase.updatePlayerName(e.player)
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (!e.item.hasItemClass) return

        if (e.action in Actions.anyRightClick) {
            when (e.item?.customItemFlag) {
                CustomItemFlag.EmberRod -> EmberRod.onPlayerUse(e.player)
                CustomItemFlag.AspectOfTheEnd -> AspectOfTheEnd.onPlayerUse(e.player)
                CustomItemFlag.MobileBank -> TODO()
            }
        }
    }

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        e.entity.killer?.let { killer ->
            if (e.entity.id == killer.id) return

            killer.send("&ayou got ${Config.tokensOnKill} ${Config.currencyName} for killing ${e.entity.displayName}")
            logger.log(
                Level.INFO,
                "gave ${Config.tokensOnKill} ${Config.currencyName} to ${killer.name} for killing ${e.entity.name}"
            )
            SkyBlockDatabase.modifyBalance(killer.id, Config.tokensOnKill, SkyBlockDatabase.BalanceModifyOperation.Add)
        }
    }
}
