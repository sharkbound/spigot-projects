package sharkbound.spigot.skyblock.plugin.extensions

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

operator fun Path.div(other: Path): Path =
    Paths.get(toString(), other.toString())


operator fun Path.div(other: String): Path =
    Paths.get(toString(), other)


operator fun String.div(other: String): Path =
    Paths.get(this, other)


operator fun String.div(other: Path): Path =
    Paths.get(this, other.toString())


operator fun File.div(other: String): File =
    File(absolutePath, other)

val String.asFile: File
    get() = File(this)
