package repack.cofh.lib.world.feature;

import static repack.cofh.lib.world.WorldGenMinableCluster.canGenerateInBlock;

import java.util.List;
import java.util.Random;

import repack.cofh.lib.util.WeightedRandomBlock;
import repack.cofh.lib.util.helpers.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FeatureGenSurface extends FeatureBase {

	final WorldGenerator worldGen;
	final int count;
	final WeightedRandomBlock[] matList;

	public FeatureGenSurface(String name, WorldGenerator worldGen, List<WeightedRandomBlock> matList, int count, GenRestriction biomeRes, boolean regen,
			GenRestriction dimRes) {

		super(name, biomeRes, regen, dimRes);
		this.worldGen = worldGen;
		this.count = count;
		this.matList = matList.toArray(new WeightedRandomBlock[matList.size()]);
	}

	@Override
	public boolean generateFeature(Random random, int chunkX, int chunkZ, World world) {

		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;

		boolean generated = false;
		for (int i = 0; i < count; i++) {
			int x = blockX + random.nextInt(16);
			int z = blockZ + random.nextInt(16);
			if (!canGenerateInBiome(world, x, z, random)) {
				continue;
			}

			int y = BlockHelper.getSurfaceBlockY(world, x, z);
			l: {
				Block block = world.getBlock(x, y, z);
				if (!block.isAir(world, x, y, z)) {

					if (canGenerateInBlock(world, x, y, z, matList)) {
						break l;
					}
				}
				continue;
			}

			generated |= worldGen.generate(world, random, x, y + 1, z);
		}
		return generated;
	}

}
