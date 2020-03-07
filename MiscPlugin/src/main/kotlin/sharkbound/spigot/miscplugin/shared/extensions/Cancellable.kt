package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.event.Cancellable

fun Cancellable.cancel() {
    isCancelled = true
}