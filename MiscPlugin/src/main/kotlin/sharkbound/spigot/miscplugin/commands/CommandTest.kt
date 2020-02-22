package sharkbound.spigot.miscplugin.commands

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import sharkbound.commonutils.extensions.filterIfNotNull
import sharkbound.commonutils.util.randDouble
import sharkbound.spigot.miscplugin.shared.extensions.registerCommand
import sharkbound.spigot.miscplugin.shared.extensions.send
import sharkbound.spigot.miscplugin.shared.utils.CancellableTask
import sharkbound.spigot.miscplugin.shared.utils.cancellingRepeatingSyncTask
import sharkbound.spigot.miscplugin.shared.utils.vector

object CommandTest : CommandExecutor {
    init {
        registerCommand("t")
    }

    var fountain: Fountain? = null

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false

        if (fountain != null) {
            fountain!!.stop()
            fountain = null
            sender.send("&aINFO: stopped fountain")
        } else {
            val mat = sender.inventory.itemInMainHand.type.filterIfNotNull { it.isBlock && !it.isAir }
            if (mat == null) {
                sender.send("&4ERROR: you need to hold a block to use this command")
                return false
            }
            fountain = Fountain(sender.location, mat.createBlockData()).apply { start() }
            sender.send("&aINFO: started a fountain of ${mat.name.capitalize()}")
        }
        return false
    }
}

class Fountain(val location: Location, val data: BlockData) {
    var task: CancellableTask? = null
    var running = false

    val vel get() = randDouble(-1.0, 1.0)
    val yVal get() = randDouble(.5, 2.0)

    fun start() {
        running = true
        task = cancellingRepeatingSyncTask(0, 1) {
            location.world?.spawnFallingBlock(location, Material.FIRE.createBlockData())?.apply {
                velocity = vector(vel, 1.0, vel)
            }
        }
    }

    fun stop() {
        running = false
        task?.cancel()
    }
}