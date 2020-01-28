package sharkbound.spigot.skyblock

import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager
import sharkbound.commonutils.collections.nonNullableMutableMapOf
import sharkbound.spigot.skyblock.enums.LocationAddMode
import sharkbound.spigot.skyblock.extensions.setBlock
import sharkbound.spigot.skyblock.plugin.SkyBlock
import sharkbound.spigot.skyblock.utils.vect
import sharkbound.spigot.skyblock.utils.worldEditSession
import java.io.File
import java.util.*
import com.sk89q.worldedit.Vector as WEVector

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

lateinit var skyBlockInstance: SkyBlock
val allWorlds get() = Bukkit.getWorlds()
val allWorldNames get() = allWorlds.asSequence().map { it.name }
val RE_REMOVE_NON_ALPHA = """[^\w]""".toRegex()
val worldEdit get() = pluginManager.getPlugin("WorldEdit") as WorldEditPlugin
val cwd get() = File(System.getProperty("user.dir"))
val cfg get() = skyBlockInstance.config
val skyIslandGenerationQueue = nonNullableMutableMapOf<UUID, PlayerSkyIslandGen>()

data class PlayerSkyIslandGen(val player: Player) {
    @Suppress("DEPRECATION")
    fun generate() {
        println("creating world for ${player.name}")
        player.world.worldEditSession {
            player.world.setBlock(player.location.subtract(vect(-1, LocationAddMode.Y)), Material.GLASS)
            Schematics.skyIslandSchematic.place(it, WEVector(0.0, Coords.SKY_ISLAND_SCHEMATIC_Y, 0.0), false)
        }
    }
}

object ConfigKeys {
    const val SKY_ISLAND_SCHEMATIC_KEY = "sky_island_schematic"
}

object Schematics {
    @Suppress("DEPRECATION")
    val skyIslandSchematic: CuboidClipboard
        get() = CuboidClipboard.loadSchematic(File(cfg.getString(ConfigKeys.SKY_ISLAND_SCHEMATIC_KEY)))
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