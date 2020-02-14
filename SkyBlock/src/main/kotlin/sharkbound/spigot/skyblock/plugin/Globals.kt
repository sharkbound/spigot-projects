package sharkbound.spigot.skyblock.plugin

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
val server get() = Bukkit.getServer()!!
val logger get() = skyBlockInstance.logger

const val PLAYER_INV_SIZE = 9 * 4
