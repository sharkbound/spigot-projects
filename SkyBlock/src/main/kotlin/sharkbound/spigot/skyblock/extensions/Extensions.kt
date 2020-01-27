package sharkbound.spigot.skyblock.extensions

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.skyblock.RE_REMOVE_NON_ALPHA
import sharkbound.spigot.skyblock.pluginManager
import sharkbound.spigot.skyblock.skyBlockInstance
import sharkbound.spigot.skyblock.utils.colorFormat
import sharkbound.spigot.skyblock.utils.deleteWorld


fun CommandExecutor.register(name: String) {
    skyBlockInstance.getCommand(name).executor = this
}

fun <T : Listener> T.register() {
    pluginManager.registerEvents(
        this,
        skyBlockInstance
    )
}

fun Player.target(distance: Int = 5000, materials: Set<Material> = setOf(Material.AIR)) =
    getTargetBlock(materials, distance)

val Player.lookLocation get() = target().location

@Suppress("DEPRECATION")
val Material.idByte
    get() = id.toByte()

inline fun <reified T> World.spawnEntityCast(location: Location, entityType: EntityType) =
    spawnEntity(location, entityType) as? T

fun Player.sendColored(message: String, char: Char = '&') = sendMessage(
    colorFormat(
        message,
        char
    )
)

val Player.skyBlockWorldName get() = "skyblock_${RE_REMOVE_NON_ALPHA.replace(name, "")}"

val Player.skyBlockWorld: World? get() = sharkbound.spigot.skyblock.utils.getWorld(skyBlockWorldName)

fun World.delete() {
    deleteWorld(name)
}

infix fun Sequence<String>.filterContainsSubstring(substr: String) =
    filter { substr.toLowerCase() in it.toLowerCase() }

infix fun Collection<String>.filterContainsSubstring(substr: String) =
    filter { substr.toLowerCase() in it.toLowerCase() }

infix fun Array<out String>.isLenOrGreater(length: Int) = len >= length
infix fun Array<out String>.isLenLessThan(length: Int) = len < length
infix fun Array<out String>.isLen(length: Int) = len == length
