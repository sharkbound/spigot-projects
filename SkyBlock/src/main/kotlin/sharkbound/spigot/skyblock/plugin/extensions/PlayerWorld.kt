package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.World
import org.bukkit.entity.Player
import sharkbound.spigot.skyblock.plugin.RE_REMOVE_NON_ALPHA

val Player.skyBlockWorldName get() = "skyblock_${RE_REMOVE_NON_ALPHA.replace(name, "")}"
val Player.skyBlockWorld: World? get() = sharkbound
    .spigot.skyblock.plugin.utils.getWorld(skyBlockWorldName)