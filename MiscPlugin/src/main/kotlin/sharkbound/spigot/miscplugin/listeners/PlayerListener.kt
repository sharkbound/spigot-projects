package sharkbound.spigot.miscplugin.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerMoveEvent
import sharkbound.commonutils.util.randDouble
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents
import sharkbound.spigot.miscplugin.shared.extensions.send
import sharkbound.spigot.miscplugin.shared.utils.CancellableTask
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.repeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.vector

object PlayerListener : Listener {
    init {
        registerEvents()
    }

    var spawn = 0


    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        fun r() =
            randDouble(-1.0, 1.0)
        if (e.player.inventory.itemInMainHand.type != Material.AIR) {
            e.isCancelled = true
            var i = 0
            cancellingRepeatingSyncTask(40, 5, { i == 20 }) {
                e.player.send(i)
                e.player.world.spawnFallingBlock(
                    e.block.location.add(vector(y = 2)), Material.GOLD_BLOCK.createBlockData()
                ).apply {
                    velocity = vector(r(), r(), r())
                }
                i += 1
            }
        }
    }

    @EventHandler
    fun onInteract(e: PlayerMoveEvent) {
        spawn += 1
        if (spawn % 10 != 0) return
    }
}