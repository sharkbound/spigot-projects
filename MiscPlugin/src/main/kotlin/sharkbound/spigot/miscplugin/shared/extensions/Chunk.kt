package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.Chunk
import org.bukkit.entity.LivingEntity

val Chunk.livingEntities: Sequence<LivingEntity>
    get() = entities.asSequence().filterIsInstance<LivingEntity>()