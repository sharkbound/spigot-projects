package sharkbound.spigot.miscplugin.shared

import org.bukkit.ChatColor

fun format(s: String, altColorChar: Char = '&') =
    ChatColor.translateAlternateColorCodes(altColorChar, s)