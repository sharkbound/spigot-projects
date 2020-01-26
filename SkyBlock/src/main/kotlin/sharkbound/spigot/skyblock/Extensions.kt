package sharkbound.spigot.skyblock

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.Listener


fun CommandExecutor.register(name: String) {
    plugin.getCommand(name).executor = this
}

fun <T : Listener> T.register() {
    pluginManager.registerEvents(this, plugin)
}

fun Player.target(distance: Int = 5000, materials: Set<Material> = setOf(Material.AIR)) =
    getTargetBlock(materials, distance)

val Player.lookLocation get() = target().location

val Material.idByte get() = id.toByte()

inline fun <reified T> World.spawnEntityCast(location: Location, entityType: EntityType) =
    spawnEntity(location, entityType) as? T

fun Player.sendColored(message: String, char: Char = '&') = sendMessage(colorFormat(message, char))

val Player.skyBlockWorldName get() = "skyblock_${RE_REMOVE_NON_ALPHA.replace(name, "")}"

val Player.skyBlockWorld: World? get() = getWorld(skyBlockWorldName)

fun World.delete() {
    deleteWorld(name)
}

fun Sequence<String>.filterContainsSubstring(substr: String) =
    filter { substr.toLowerCase() in it.toLowerCase() }