package sharkbound.spigot.skyblock.plugin.objects

import sharkbound.spigot.skyblock.plugin.cfg

object Config {
    val skyIslandSchematicString
        get() = cfg.getString(ConfigKeys.skyIslandSchematicKey)

    val cowSpawnEggName
        get() = cfg.getString(ConfigKeys.cowSpawnEggName)

    val tokensOnKill
        get() = cfg.getInt(ConfigKeys.tokensOnKill)

    val tokenName
        get() = cfg.getString(ConfigKeys.tokenName)

    val emberRodCost
        get() = cfg.getInt(ConfigKeys.emberRodCost)

    val aspectOfTheEndCost
        get() = cfg.getInt(ConfigKeys.aspectOfTheEndCost)

    val aspectOfTheEndRange
        get() = cfg.getInt(ConfigKeys.aspectOfTheEndRange)

}