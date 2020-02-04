@file:Suppress("DEPRECATION")

package sharkbound.spigot.skyblock.plugin.objects

import com.sk89q.worldedit.CuboidClipboard
import sharkbound.spigot.skyblock.plugin.cfg
import java.io.File

object Schematics {
    val skyIslandSchematic: CuboidClipboard
        get() = CuboidClipboard.loadSchematic(
            File(
                cfg.getString(
                    ConfigKeys.skyIslandSchematicKey
                )
            )
        )
}