package sharkbound.spigot.skyblock

import com.sk89q.worldedit.bukkit.WorldEditPlugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.PluginManager
import sharkbound.spigot.skyblock.plugin.SkyBlock
import sun.security.pkcs11.Secmod
import java.io.File

val pluginManager: PluginManager
    get() = Bukkit.getPluginManager()

lateinit var skyBlockInstance: SkyBlock
val overworld get() = Bukkit.getWorld("world")!!
val allWorlds get() = Bukkit.getWorlds()
val allWorldNames get() = allWorlds.asSequence().map { it.name }
val RE_REMOVE_NON_ALPHA = """[^\w]""".toRegex()
val worldEdit get() = pluginManager.getPlugin("WorldEdit") as WorldEditPlugin
val cwd get() = File(System.getProperty("user.dir"))
val cfg get() = skyBlockInstance.config

object ConfigKeys {
    const val SKY_ISLAND_SCHEMATIC = "sky_island_schematic"
}

object Coords {
    const val SKY_ISLAND_SCHEMATIC_Y = 0.0
}