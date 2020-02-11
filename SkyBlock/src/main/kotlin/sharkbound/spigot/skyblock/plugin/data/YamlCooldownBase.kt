package sharkbound.spigot.skyblock.plugin.data

import sharkbound.spigot.skyblock.plugin.utils.currentMilliseconds
import java.text.DecimalFormat
import java.util.*

open class YamlCooldownBase(fileName: String, private val cooldown: Double = 0.0) : YamlBase(fileName) {
    open val format = DecimalFormat("#0.0")

    open fun onCooldown(uuid: UUID, cooldownSeconds: Double = cooldown): Boolean =
        uuid.toString().let {
            it in config && (currentMilliseconds.toDouble() - config
                .getLong(it).toDouble()) <= cooldownSeconds * 1000
        }

    open fun remainingCooldown(uuid: UUID, cooldownSeconds: Double = cooldown) =
        uuid.toString()
            .let {
                if (it !in config) 0.0 else cooldownSeconds - ((currentMilliseconds.toDouble() - config
                    .getLong(it).toDouble()) / 1000)
            }

    open fun remainingCooldownFormatted(uuid: UUID, cooldownSeconds: Double = cooldown): String =
        format.format(remainingCooldown(uuid, cooldownSeconds))

    open fun update(uuid: UUID) {
        config[uuid.toString()] = currentMilliseconds
        save()
    }
}