package sharkbound.spigot.skyblock

import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.bukkit.WorldEditPlugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager
import sharkbound.commonutils.collections.nonNullableMutableMapOf
import sharkbound.spigot.skyblock.plugin.SkyBlock
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

lateinit var skyBlockInstance: SkyBlock
//val overworld get() = Bukkit.getWorld("world")!!
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
        println(cfg.getString(ConfigKeys.SKY_ISLAND_SCHEMATIC))
        worldEdit.createEditSession(player).also {
            Schematics.skyIslandSchematic.place(it, Vector(0.0, Coords.SKY_ISLAND_SCHEMATIC_Y, 0.0), false)
        }
    }
}

object ConfigKeys {
    const val SKY_ISLAND_SCHEMATIC = "sky_island_schematic"
}

object Schematics {
    @Suppress("DEPRECATION")
    val skyIslandSchematic: CuboidClipboard
        get() = CuboidClipboard.loadSchematic(File(cfg.getString(ConfigKeys.SKY_ISLAND_SCHEMATIC)))
}

object Coords {
    const val SKY_ISLAND_SCHEMATIC_Y = 0.0
}

