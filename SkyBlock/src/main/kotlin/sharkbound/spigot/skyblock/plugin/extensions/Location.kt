package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Location

fun Location.setYawPitch(yaw: Float, pitch: Float) =
    also {
        it.yaw = yaw
        it.pitch = pitch
    }

inline fun Location.cloneApply(block: Location.() -> Unit) =
    clone().apply(block)

inline fun Location.cloneAlso(block: (Location) -> Unit) =
    clone().also(block)

inline fun <R> Location.cloneRun(block: Location.() -> R) =
    clone().run(block)

inline fun <R> Location.cloneLet(block: (Location) -> R) =
    clone().let(block)

inline fun Location.clone(block: (Location) -> Unit) =
    apply(block)