package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.Material
import org.bukkit.block.BlockState
import org.bukkit.block.Chest

val BlockState.asChest get() = block?.state as? Chest
val BlockState.isChest get() = type == Material.CHEST