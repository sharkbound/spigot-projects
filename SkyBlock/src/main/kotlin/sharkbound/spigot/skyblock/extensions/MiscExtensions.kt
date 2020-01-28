package sharkbound.spigot.skyblock.extensions

infix fun String?.lowerEq(other: String?) =
    when {
        this != null -> false
        other != null -> false
        else -> equals(other, ignoreCase = true)
    }