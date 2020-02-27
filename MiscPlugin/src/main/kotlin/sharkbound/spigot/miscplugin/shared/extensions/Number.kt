package sharkbound.spigot.miscplugin.shared.extensions

val Int.ticks
    get() = toLong()

val Double.secondTicks
    get() = (this / 20).toLong()

val Int.secondTicks
    get() = toDouble().secondTicks