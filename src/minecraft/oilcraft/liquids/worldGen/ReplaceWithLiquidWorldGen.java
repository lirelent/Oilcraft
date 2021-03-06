/*
 *  Copyright 2013 Ryan Metzger
 *
 *  This file is part of OilCraft.
 *
 *  OilCraft is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  OilCraft is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with OilCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package oilcraft.liquids.worldGen;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Uses information in {@link ToGenerateLiquid} stucts to replace blocks not
 * excluded with specified liquid.
 * 
 * @author lirelent
 */
public class ReplaceWithLiquidWorldGen implements IWorldGenerator {
	private Set<ToGenerateLiquid> toGenerateLiquids = new HashSet<ToGenerateLiquid>();

	public ReplaceWithLiquidWorldGen() {
		GameRegistry.registerWorldGenerator(this);
	}

	/**
	 * the new liquid {@link ToGenerateLiquid} contains all the needed
	 * information about how you want your new liquid to be placed in the world.
	 * This adds the liquid to the list of liquids to be generated in the world.
	 * If you've already added this liquid it will overwrite the previously
	 * added one.
	 * 
	 * @param newLiquid
	 *            the new liquid we want in the world
	 * @param name
	 *            the name to register this liquid with in
	 *            {@link LiquidDictionary}
	 */
	public void add(ToGenerateLiquid newLiquid, String name) {
		if (!this.toGenerateLiquids.contains(newLiquid)) {
			// this means that this liquid has never been added before and needs
			// to be added to the liquid dictionary
			LiquidDictionary.getOrCreateLiquid(name, new LiquidStack(
					newLiquid.blockId, 1));
		} else {
			this.toGenerateLiquids.remove(newLiquid);
		}
		this.toGenerateLiquids.add(newLiquid);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		// only for the "Overworld"
		if (world.provider.dimensionId == 0) {
			// convert to block coordinates
			chunkX = chunkX << 4;
			chunkZ = chunkZ << 4;
			for (ToGenerateLiquid liquid : this.toGenerateLiquids) {
				for (int x = chunkX; x < chunkX + 16; ++x) {
					for (int z = chunkZ; z < chunkZ + 16; ++z) {
						for (int level = liquid.minWorldLevel; level <= liquid.maxWorldLevel; ++level) {
							if (world.getBlockId(x, level, z) != 0) {
								if (!world.setBlock(x, level, z, liquid.blockId, 0, 2)) {
									FMLLog.warning("Could not place oil at "
											+ x + ", " + level + ", " + z);
								}
							}
						}
					}
				}
			}
		}
		// don't replace block id 0 which is air
	}
}
