package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.spigot.skyblock.plugin.database.SkyBlockDatabase
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.logger
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.SpecialItemFlags
import sharkbound.spigot.skyblock.plugin.specialitems.AspectOfTheEnd
import sharkbound.spigot.skyblock.plugin.specialitems.EmberRod
import java.util.logging.Level

class PlayerEventListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        SkyBlockDatabase.initPlayer(e.player)
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (!e.item.hasItemClass) return

        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            when (e.item?.specialItemFlag) {
                SpecialItemFlags.EmberRod -> EmberRod.activate(e.player)
                SpecialItemFlags.AspectOfTheEnd -> AspectOfTheEnd.activate(e.player)
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
