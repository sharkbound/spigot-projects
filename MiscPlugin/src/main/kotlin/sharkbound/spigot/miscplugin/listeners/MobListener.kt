package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import sharkbound.spigot.miscplugin.shared.extensions.color
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents
import sharkbound.spigot.miscplugin.shared.logger

object MobListener : Listener {
    init {
        registerEvents()
    }

    fun format(value: Double) =
        "%.1f".format(value)

    @EventHandler
    fun mobHurt(e: EntityDamageEvent) {
        e.entity.let {
            logger.info("${it is LivingEntity}")
            if (it !is LivingEntity) return
            it.customName = "&e&l${format(it.health)}".color()
            it.isCustomNameVisible = true
        }
    }
}