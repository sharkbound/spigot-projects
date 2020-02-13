package sharkbound.spigot.skyblock.plugin.customitems

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import sharkbound.spigot.skyblock.plugin.builders.buildItem
import sharkbound.spigot.skyblock.plugin.extensions.colored
import sharkbound.spigot.skyblock.plugin.extensions.send
import sharkbound.spigot.skyblock.plugin.objects.Config
import sharkbound.spigot.skyblock.plugin.objects.CustomItemFlag
import sharkbound.spigot.skyblock.plugin.objects.ItemTier
import sharkbound.spigot.skyblock.plugin.objects.Text
import sharkbound.spigot.skyblock.plugin.utils.colorAll

object MobileBank : CustomItemBase() {
    override val itemName = "&6Mobile Bank".colored()
    override val itemLore = colorAll(Text.createLoreTier(ItemTier.NORMAL))
    override val tier = ItemTier.NORMAL
    override val price
        get() = Config.mobileBankCost

    override fun createItem(): ItemStack {
        return buildItem {
            displayName(itemName)
            material(Material.SKULL_ITEM)
            specialItemFlag(CustomItemFlag.MobileBank)
            metaFlags(ItemFlag.HIDE_ATTRIBUTES)
            metaCast<SkullMeta> {
                // todo get player skin working on the skull
                owner = "Piggy Bank"/*Config.mobileBankSkullOwnerUUID*/
            }
        }
    }

    override fun onPlayerUse(player: Player) {
        player.send("HI!")
    }

}