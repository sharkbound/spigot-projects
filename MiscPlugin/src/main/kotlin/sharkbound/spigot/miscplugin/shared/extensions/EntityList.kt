package sharkbound.spigot.miscplugin.shared.extensions

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

fun <T : Collection<Entity>> T.living() =
    filterIsInstance<LivingEntity>()

fun Array<Entity>.living() =
    filterIsInstance<LivingEntity>()

fun Sequence<Entity>.living() =
    filterIsInstance<LivingEntity>()