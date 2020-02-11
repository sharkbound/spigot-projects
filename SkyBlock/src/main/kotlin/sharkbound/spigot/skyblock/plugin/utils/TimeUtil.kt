package sharkbound.spigot.skyblock.plugin.utils

val currentSeconds: Double
    get() = System.currentTimeMillis().toDouble() / 1000.0

val currentMilliseconds: Long
    get() = System.currentTimeMillis()