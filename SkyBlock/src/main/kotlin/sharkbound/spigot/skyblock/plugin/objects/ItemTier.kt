package sharkbound.spigot.skyblock.plugin.objects

import sharkbound.spigot.skyblock.plugin.extensions.colored

enum class ItemTier(val baseName: String, val color: String) {
    EPIC("EPIC", "&5"),
    SUPER("SUPER", "&a"),
    NORMAL("NORMAL", "&6");

    val colored =
        "&r$color$baseName&r".colored()

    override fun toString() =
        colored
}