package sharkbound.spigot.skyblock.plugin.objects

internal object NbtTags {
    const val ITEM_CLASS = "itemClass"
}

enum class SpecialItemFlags(val nbtValue: String) {
    EmberRod("emberrod"),
    AspectOfTheEnd("aspectoftheend"),
}