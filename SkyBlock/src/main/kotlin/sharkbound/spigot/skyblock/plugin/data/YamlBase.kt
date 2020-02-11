package sharkbound.spigot.skyblock.plugin.data

import org.bukkit.configuration.file.YamlConfiguration
import sharkbound.commonutils.extensions.closeAfter
import sharkbound.spigot.skyblock.plugin.extensions.div
import sharkbound.spigot.skyblock.plugin.objects.FilePaths
import java.io.FileWriter

open class YamlBase(configFile: String) {
    protected val path = FilePaths.configFolder / configFile
    protected val config: YamlConfiguration

    val exists
        get() = path.toFile().exists()

    init {
        if (exists) {
            FileWriter(path.toString()).closeAfter {
                write("")
            }
        }
        config = YamlConfiguration.loadConfiguration(path.toFile())
    }

    protected fun save() {
        config.save(path.toString())
    }
}