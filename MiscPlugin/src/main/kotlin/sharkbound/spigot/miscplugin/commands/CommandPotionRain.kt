package sharkbound.spigot.miscplugin.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.EntityType
import org.bukkit.entity.LingeringPotion
import org.bukkit.entity.SplashPotion
import org.bukkit.entity.ThrownPotion
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType
import sharkbound.commonutils.extensions.choice
import sharkbound.commonutils.extensions.len
import sharkbound.commonutils.util.randDouble
import sharkbound.spigot.miscplugin.shared.builders.buildItem
import sharkbound.spigot.miscplugin.shared.extensions.*
import sharkbound.spigot.miscplugin.shared.utils.CancellableTask
import sharkbound.spigot.miscplugin.shared.utils.repeatingSyncTask
import kotlin.contracts.ExperimentalContracts

object CommandPotionRain : BaseCommand("potionrain") {

    var task: CancellableTask? = null
    val allEffects = enumValues<PotionType>()

    @ExperimentalContracts
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.isPlayer()) return false

        val interval = args.getOrElse(0) { "2" }.toLong()
        val radius = args.getOrElse(1) { "50" }.toDouble()

        task?.cancel()
        task = null

        if (args.len == 0) {
            sender.send("&eCancelled potion rain")
            return false
        }

        sender.apply {
            task = repeatingSyncTask(0, interval) {
                val dropLocation = location.clone().apply {
                    x += randDouble(-radius, radius)
                    y = sender.location.y - 2
                    z += randDouble(-radius, radius)
                }
                world.spawnAs<ThrownPotion>(dropLocation)?.let {
                    it.item =
                        Potion(allEffects.choice(), 1, true).toItemStack(1)
                }
            }
        }

        return false
    }
}