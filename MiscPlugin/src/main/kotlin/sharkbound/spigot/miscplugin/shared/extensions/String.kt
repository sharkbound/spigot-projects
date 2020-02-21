package sharkbound.spigot.miscplugin.shared.extensions

fun String.format(altColorChar: Char = '&') =
    sharkbound.spigot.miscplugin.shared.format(this, altColorChar)