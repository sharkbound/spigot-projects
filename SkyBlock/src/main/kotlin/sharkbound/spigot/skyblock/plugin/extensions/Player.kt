package sharkbound.spigot.skyblock.plugin.extensions

import org.bukkit.entity.Player
import java.util.*

fun Player.send(message: String, altColorChar: Char = '&') =
    sendMessage(message.colored(altColorChar))

fun Player.send(obj: Any, altColorChar: Char = '&') =
    send(obj.toString(), altColorChar)

val Player.strId
    get() = id.toString()

val Player.id: UUID
    get() = uniqueId

val Player.yaw
    get() = location.yaw

val Player.pitch
    get() = location.pitch
