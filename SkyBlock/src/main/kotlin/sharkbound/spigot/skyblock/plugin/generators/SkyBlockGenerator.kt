package sharkbound.spigot.skyblock.plugin.generators

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import sharkbound.spigot.skyblock.idByte
import java.util.*


class VoidChunkGenerator : ChunkGenerator() {

    override fun generateBlockSections(
        world: World?,
        random: Random?,
        x: Int,
        z: Int,
        biomes: BiomeGrid?
    ): Array<ByteArray> {
        val result = arrayOfNulls<ByteArray>(world!!.maxHeight / 16)
//        for (ix in 0..15) {
//            for (iy in 0..15) {
//                for (iz in 0..15) {
//                    result.block(ix, iy, iz, Material.AIR)
//                }
//            }
//        }
        return result as Array<ByteArray>
    }

    private fun Array<ByteArray?>.block(
        x: Int,
        y: Int,
        z: Int,
        block: Material
    ) {
        // is this chunk part already initialized?
        if (this[y shr 4] == null) {
            // Initialize the chunk part
            this[y shr 4] = ByteArray(4096)
        }
        // set the block (look above, how this is done)
        this[y shr 4]!![(y and 0xF shl 8 or (z shl 4) or x)] = block.idByte
    }

    override fun canSpawn(world: World?, x: Int, z: Int): Boolean {
        return true
    }
}