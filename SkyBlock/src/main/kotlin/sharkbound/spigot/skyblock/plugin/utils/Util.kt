package sharkbound.spigot.skyblock.plugin.utils

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.bukkit.BukkitWorld
import org.bukkit.*
import org.bukkit.command.CommandExecutor
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.PluginManager
import org.bukkit.util.Vector
import sharkbound.commonutils.extensions.len
import sharkbound.spigot.skyblock.plugin.SkyBlock
import sharkbound.spigot.skyblock.plugin.commands.*
import sharkbound.spigot.skyblock.plugin.enums.CoordPosition
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.gui.InventoryGuiClickListener
import sharkbound.spigot.skyblock.plugin.listeners.PlayerEventListener
import sharkbound.spigot.skyblock.plugin.listeners.SkyBlockWorldChangeListener
import sharkbound.spigot.skyblock.plugin.objects.WorldEditConstants
import sharkbound.spigot.skyblock.plugin.pluginManager
import sharkbound.spigot.skyblock.plugin.skyBlockInstance

val allCommands = mutableListOf<CommandExecutor>()
val allEventListeners = mutableListOf<Listener>()

fun registerAllCommands() {
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

fun registerAllEventListeners() {
    allEventListeners.addAll(
        listOf(
            SkyBlockWorldChangeListener(),
            InventoryGuiClickListener(),
            PlayerEventListener()
        )
    )
}

fun colored(message: String, char: Char = '&') =
    ChatColor.translateAlternateColorCodes(char, message)

inline fun usePlugin(func: SkyBlock.() -> Unit) {
    skyBlockInstance.func()
}

inline fun usePluginManager(func: PluginManager.() -> Unit) {
    pluginManager.func()
}

fun cannotBeCalledFromConsole(): Boolean {
    println("this command cannot be called from console")
    return false
}

fun Array<out String>.wrongArgsLength(required: Int, msg: String? = null, usage: String? = null): Boolean {
    when {
        msg != null -> println("missing required arguments, required: $required, actual: $len, message: $msg")
        usage != null -> println("missing required arguments, required: $required, actual: $len, usage: $usage")
        else -> println("missing required arguments, required: $required, actual: $len")
    }
    return false
}

fun Array<out String>.wrongArgsLength(
    caller: Player,
    required: Int,
    msg: String? = null,
    usage: String? = null
): Boolean {
    when {
        msg != null -> caller.send("&4missing required arguments, required: $required, actual: $len, message: $msg")
        usage != null -> caller.send("&4missing required arguments, required: $required, actual: $len, usage: $usage")
        else -> caller.send("&4missing required arguments, required: $required, actual: $len")
    }
    return false
}

fun vect(x: Int = 0, y: Int = 0, z: Int = 0) =
    Vector(x, y, z)

fun vect(x: Double = 0.0, y: Double = 0.0, z: Double = 0.0) =
    Vector(x, y, z)

fun vect(value: Int, mode: CoordPosition) =
    when (mode) {
        CoordPosition.X -> vect(value, 0, 0)
        CoordPosition.Y -> vect(0, value, 0)
        CoordPosition.Z -> vect(0, 0, value)
    }

fun vect(value: Double, mode: CoordPosition) =
    when (mode) {
        CoordPosition.X -> vect(value, 0.0, 0.0)
        CoordPosition.Y -> vect(0.0, value, 0.0)
        CoordPosition.Z -> vect(0.0, 0.0, value)
    }

@Suppress("DEPRECATION")
inline fun World.worldEditSession(handler: (EditSession) -> Unit) =
    EditSession(BukkitWorld(this), WorldEditConstants.MAX_WORLD_EDIT_BLOCKS).apply(handler)

fun Collection<String>.colorAll(): List<String> =
    map { it.colored() }

fun colorAll(vararg strings: String): List<String> =
    strings.map { it.colored() }

fun newStack(material: Material, amount: Int = 1, damage: Short = 0): ItemStack =
    ItemStack(material, amount, damage)