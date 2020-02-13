package sharkbound.spigot.skyblock.plugin.objects

internal object NbtTags {
    const val ITEM_CLASS = "itemClass"
}

enum class CustomItemFlag(val nbtValue: String) {
    EmberRod("emberrod"),
    AspectOfTheEnd("aspectoftheend"),
    MobileBank("mobilebank"),
}