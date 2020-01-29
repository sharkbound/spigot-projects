package sharkbound.spigot.skyblock.plugin

import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.PluginManager
import sharkbound.commonutils.collections.nonNullableMutableMapOf
import sharkbound.spigot.skyblock.plugin.generators.PlayerSkyIslandGenerator
import java.io.File
import java.util.*

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

lateinit var skyBlockInstance: SkyBlock
val allWorlds get() = Bukkit.getWorlds()
val allWorldNames get() = allWorlds.asSequence().map { it.name }
val RE_REMOVE_NON_ALPHA = """[^\w]""".toRegex()
val worldEdit get() = pluginManager.getPlugin("WorldEdit") as WorldEditPlugin
val cwd get() = File(System.getProperty("user.dir"))
val cfg get() = skyBlockInstance.config
val skyIslandGenerationQueue = nonNullableMutableMapOf<UUID, PlayerSkyIslandGenerator>()

object ConfigKeys {
    const val skyIslandSchematicKey = "sky_island_schematic"
    const val cowSpawnEggName = "cowSpawnEggName"
}

object Schematics {
    @Suppress("DEPRECATION")
    val skyIslandSchematic: CuboidClipboard
        get() = CuboidClipboard.loadSchematic(
            File(
                cfg.getString(
                    ConfigKeys.skyIslandSchematicKey
                )
            )
        )
}

object Coords {
    const val SKY_ISLAND_SCHEMATIC_Y = 60.0
    val skyIslandSpawnOffset
        get() =
            org.bukkit.util.Vector(13, 21, 25)
}

object WorldEditConstants {
    const val MAX_WORLD_EDIT_BLOCKS = 999999999
}