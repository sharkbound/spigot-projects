package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.ChatColor

fun String.colorFormat(char: Char = '&') =
    ChatColor.translateAlternateColorCodes(char, this)