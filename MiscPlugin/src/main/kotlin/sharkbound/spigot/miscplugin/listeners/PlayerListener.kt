package sharkbound.spigot.miscplugin.listeners

import net.minecraft.server.v1_14_R1.NBTTagList
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Phantom
import org.bukkit.entity.Player
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import sharkbound.commonutils.util.randInt
import sharkbound.spigot.miscplugin.items.ShulkerWand
import sharkbound.spigot.miscplugin.shared.enums.NBTTags
import sharkbound.spigot.miscplugin.shared.enums.allTags
import sharkbound.spigot.miscplugin.shared.enums.buildNBTCompound
import sharkbound.spigot.miscplugin.shared.enums.buildNBTTagList
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.utils.vector

object PlayerListener : Listener {
    init {
        registerEvents()
    }

    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        if (e.item?.nbt?.getString("type") == ShulkerWand.nbtId) {

        }
    }
}