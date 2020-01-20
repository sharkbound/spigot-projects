package sharkbound.spigot.skyblock.plugin.generators

import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*


class SkyBlockChunkGenerator : ChunkGenerator() {

    override fun generateBlockSections(
        world: World?,
        random: Random?,
        x: Int,
        z: Int,
        biomes: BiomeGrid?
    ): Array<ByteArray> {
        return super.generateBlockSections(world, random, x, z, biomes)
    }

    private fun Array<ByteArray?>.setBlock(
        x: Int,
        y: Int,
        z: Int,
        blockId: Byte
    ) { // is this chunk part already initialized?
        if (this[y shr 4] == null) { // Initialize the chunk part
            this[y shr 4] = ByteArray(4096)
        }
        // set the block (look above, how this is done)
        this[y shr 4]!![(y and 0xF shl 8 or (z shl 4) or x)] = blockId
    }

    override fun canSpawn(world: World?, x: Int, z: Int): Boolean {
        return true
    }
}