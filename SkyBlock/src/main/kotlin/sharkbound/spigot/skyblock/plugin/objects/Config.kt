package sharkbound.spigot.skyblock.plugin.objects

import sharkbound.spigot.skyblock.plugin.cfg

object Config {
    val skyIslandSchematicString
        get() = cfg.getString("sky_island_schematic")

    val cowSpawnEggName
        get() = cfg.getString("cow_spawn_egg_name")

    val tokensOnKill
        get() = cfg.getInt("tokens_on_kill")

    val currencyName
        get() = cfg.getString("currency_name")

    val emberRodCost
        get() = cfg.getInt("item_costs.ember_rod_cost")

    val aspectOfTheEndCost
        get() = cfg.getInt("item_costs.aspect_of_the_end")

    val aspectOfTheEndRange
        get() = cfg.getInt("item_settings.aspect_of_the_end_range")

    val aspectOfTheEndWallYAutoCorrect
        get() = cfg.getInt("item_settings.aspect_of_the_end_wall_y_autocorrect")

    val aspectOfTheEndCooldown
        get() = cfg.getDouble("cooldowns.aspect_of_the_end")

    val emberRodCooldown
        get() = cfg.getDouble("cooldowns.ember_rod")
}