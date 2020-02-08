@file:Suppress("DEPRECATION")

package sharkbound.spigot.skyblock.plugin.objects

import com.sk89q.worldedit.CuboidClipboard
import java.io.File

object Schematics {
    val skyIslandSchematic: CuboidClipboard
        get() = CuboidClipboard.loadSchematic(File(Config.skyIslandSchematicString))
}