package sharkbound.spigot.miscplugin.listeners

import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import sharkbound.spigot.miscplugin.shared.extensions.color
import sharkbound.spigot.miscplugin.shared.extensions.registerEvents
import sharkbound.spigot.miscplugin.shared.extensions.send
import sharkbound.spigot.miscplugin.shared.server

object MobListener : Listener {
    init {
        registerEvents()
    }

    private fun format(value: Double) =
        "%.1f".format(value)

//    @EventHandler
//    fun entityDeath(e: EntityDeathEvent) {
//        server.getPlayer("landsharkslayer")?.send("&4Entity ${e.entity.type.name} died")
//    }

//    @EventHandler(priority = EventPriority.LOWEST)
//    fun mobHurt(e: EntityDamageEvent) {
//        e.entity.let {
//            if (it !is LivingEntity) return
//            it.customName = "&5&l${format(it.health - e.damage)}----------${format(it.health - e.damage)}".color()
//            it.isCustomNameVisible = true
//        }
//    }
}