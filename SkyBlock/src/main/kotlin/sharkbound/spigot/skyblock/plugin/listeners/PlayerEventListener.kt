package sharkbound.spigot.skyblock.plugin.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerJoinEvent
import sharkbound.spigot.skyblock.plugin.database.DB
import sharkbound.spigot.skyblock.plugin.extensions.*
import sharkbound.spigot.skyblock.plugin.logger
import sharkbound.spigot.skyblock.plugin.objects.Config
import java.util.logging.Level

class PlayerEventListener : Listener {
    init {
        register()
    }

    @EventHandler
    fun itemEquipped(e: PlayerItemHeldEvent) {
        e.newItem?.let {
            if (it.hasTag("test")) {
                println(it.name)
            }
        }
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        DB.initPlayer(e.player)
    }

    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        e.entity.killer?.let { killer ->
            if (e.entity.id == killer.id) return

            killer.send("&ayou got ${Config.tokensOnKill} ${Config.tokenName} for killing ${e.entity.displayName}")
            logger.log(
                Level.INFO,
                "gave ${Config.tokensOnKill} ${Config.tokenName} to ${killer.name} for killing ${e.entity.name}"
            )
            DB.modifyBalance(killer.id, Config.tokensOnKill, DB.BalanceModifyOperation.Add)
        }
    }
}
