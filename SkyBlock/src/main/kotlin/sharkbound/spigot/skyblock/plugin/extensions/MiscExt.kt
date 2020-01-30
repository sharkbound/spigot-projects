package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.ChatColor
import org.bukkit.event.Cancellable

fun String.colorFormat(char: Char = '&') =
    ChatColor.translateAlternateColorCodes(char, this)

fun Cancellable.cancel() {
    isCancelled = true
}