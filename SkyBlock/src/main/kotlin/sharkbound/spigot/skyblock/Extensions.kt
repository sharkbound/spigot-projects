package sharkbound.spigot.skyblock

import org.bukkit.Material
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

fun CommandExecutor.register(name: String) {
    plugin.getCommand(name).executor = this
}

fun <T : Listener> T.register() {
    pluginManager.registerEvents(this, plugin)
}

fun Player.target(distance: Int = 5000, blocks: Set<Material> = setOf(Material.AIR)) =
    getTargetBlock(blocks, distance)

val Player.lookLocation get() = target().location