package sharkbound.spigot.miscplugin.shared.extensions

import sharkbound.spigot.miscplugin.shared.colored

fun String.color(altColorChar: Char = '&') =
    colored(this, altColorChar)