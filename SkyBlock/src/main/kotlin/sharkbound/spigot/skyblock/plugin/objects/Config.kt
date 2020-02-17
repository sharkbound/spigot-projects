package sharkbound.spigot.skyblock.plugin.objects

import sharkbound.spigot.skyblock.plugin.cfg

object Config {
    val skyIslandSchematicString
        get() = cfg.getString("sky_island_schematic")

    val cowSpawnEggName
        get() = cfg.getString("cow_spawn_egg_name")

    val paymentOnKill
        get() = cfg.getInt("payment_on_kill")

    val currencyName
        get() = cfg.getString("currency_name")

    val emberRodCost
        get() = cfg.getInt("item_costs.ember_rod")

    val aspectOfTheEndCost
        get() = cfg.getInt("item_costs.aspect_of_the_end")

    val mobileBankCost
        get() = cfg.getInt("item_costs.mobile_bank")

    val aspectOfTheEndRange
        get() = cfg.getInt("item_settings.aspect_of_the_end_range")

    val aspectOfTheEndWallYAutoCorrect
        get() = cfg.getInt("item_settings.aspect_of_the_end_wall_y_autocorrect")

    val aspectOfTheEndCooldown
        get() = cfg.getDouble("cooldowns.aspect_of_the_end")

    val emberRodCooldown
        get() = cfg.getDouble("cooldowns.ember_rod")

    val mobileBankCooldown
        get() = cfg.getDouble("cooldowns.mobile_bank")

    val mobileBankSkullOwnerUserName
        get() = cfg.getString("item_settings.mobile_bank_skull_owner_username")

    val usableCoinCooldown
        get() = cfg.getDouble("cooldowns.coin")

}