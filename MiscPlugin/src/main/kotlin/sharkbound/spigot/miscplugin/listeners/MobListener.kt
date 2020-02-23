package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import sharkbound.spigot.miscplugin.shared.extensions.color
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents

object MobListener : Listener {
    init {
        registerEvents()
    }

    private fun format(value: Double) =
        "%.1f".format(value)

    @EventHandler(priority = EventPriority.LOWEST)
    fun mobHurt(e: EntityDamageEvent) {
        e.entity.let {
            if (it !is LivingEntity) return
            it.customName = "&5&l${format(it.health - e.damage)}".color()
            it.isCustomNameVisible = true
        }
    }
}