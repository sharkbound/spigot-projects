package sharkbound.spigot.miscplugin.shared

import org.bukkit.ChatColor

fun colored(s: String, altColorChar: Char = '&') =
    ChatColor.translateAlternateColorCodes(altColorChar, s)