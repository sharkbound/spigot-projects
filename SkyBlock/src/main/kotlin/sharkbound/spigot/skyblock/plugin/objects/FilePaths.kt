package sharkbound.spigot.skyblock.plugin.objects

import sharkbound.commonutils.util.asPath
import sharkbound.spigot.skyblock.plugin.skyBlockInstance
import java.nio.file.Path

object FilePaths {
    val configFolder: Path
        get() = skyBlockInstance.dataFolder.absolutePath.asPath

    val worldFolder: Path
        get() = "".asPath.toAbsolutePath() /*server.worldContainer.absoluteFile.toPath()*/
}