package sharkbound.spigot.miscplugin.shared.extensions

fun Class<*>.method(name: String, vararg types: Class<*>) =
    getDeclaredMethod(name, *types).apply { isAccessible = true }