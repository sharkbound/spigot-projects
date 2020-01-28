package sharkbound.spigot.skyblock.extensions

import sharkbound.commonutils.extensions.len

infix fun Sequence<String>.filterContainsSubstring(substr: String) =
    filter { substr.toLowerCase() in it.toLowerCase() }

infix fun Collection<String>.filterContainsSubstring(substr: String) =
    filter { substr.toLowerCase() in it.toLowerCase() }

infix fun Array<out String>.isLenOrGreater(length: Int) = len >= length
infix fun Array<out String>.isLenLessThan(length: Int) = len < length
infix fun Array<out String>.isLen(length: Int) = len == length