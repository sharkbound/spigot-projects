package sharkbound.spigot.miscplugin.shared.ext

import org.bukkit.entity.Player
import sharkbound.spigot.miscplugin.shared.colored

fun Player.send(obj: Any?, altColorChar: Char = '&') {
    sendMessage(colored(obj.toString(), altColorChar))
}