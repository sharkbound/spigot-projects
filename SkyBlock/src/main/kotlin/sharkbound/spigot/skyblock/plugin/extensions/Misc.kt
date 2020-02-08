package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.ChatColor
import org.bukkit.event.Cancellable
import java.util.*

fun String.colored(char: Char = '&'): String =
    ChatColor.translateAlternateColorCodes(char, this)

fun Cancellable.cancel() {
    isCancelled = true
}

val String.toUUID: UUID
    get() = UUID.fromString(this)

val Long.secondTicks
    get() = this * 20

val Int.secondTicks
    get() = (this * 20).toLong()

val Int.ticks
    get() = toLong()

val String.normalized
    get() = toLowerCase().trim()