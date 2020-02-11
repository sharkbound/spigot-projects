package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Location

fun Location.yawPitch(yaw: Float, pitch: Float) =
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

fun Location.keepYawPitch(other: Location): Location =
    cloneApply {
        yaw = other.yaw
        pitch = other.pitch
    }