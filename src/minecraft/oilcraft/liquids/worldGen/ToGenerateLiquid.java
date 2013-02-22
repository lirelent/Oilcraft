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

/**
 * This represents a new liquid to be added into world generation. It's a struct
 * of the information the world gen plugin needs to know in order to add this
 * liquid to the world and how you want it added. The generator will only replace
 * none air or tree blocks.
 * 
 * @author lirelent
 */
public class ToGenerateLiquid {
	/**
	 * randomly put the bottom of veins between these two world levels
	 */
	public final int minWorldLevel, maxWorldLevel;
	/**
	 * For each chunk the number of pools to generate is the floor of this
	 * number times a random value between 0.0 and 1.0.
	 */
	public final float perChunkDensity;
	/**
	 * The range of horizontal radiuses of pools to be generated in blocks from a central
	 * block.
	 */
	public final int minVeinRadius, maxVeinRadius;
	/**
	 * the ratio between the width to the height of all generated pools. Eg. a value of 10 would
	 * make broad shallow pools
	 */
	public final float ratioWidthHeight;
	/**
	 * The block id of the still liquid that we want to add into the world.
	 */
	public final int blockId;
	
	/**
	 * @param minWorldLevel {@link #minWorldLevel}
	 * @param maxWorldLevel {@link #maxWorldLevel}
	 * @param perChunkDensity {@link #perChunkDensity}
	 * @param minVeinRadius {@link #minVeinRadius}
	 * @param maxVeinRadius {@link #maxVeinRadius}
	 * @param ratioWidthHeight {@link #ratioWidthHeight}
	 * @param blockId {@link #blockId}
	 */
	public ToGenerateLiquid(int minWorldLevel, int maxWorldLevel,
			float perChunkDensity, int minVeinRadius, int maxVeinRadius,
			float ratioWidthHeight, int blockId) {
		super();
		this.minWorldLevel = minWorldLevel;
		this.maxWorldLevel = maxWorldLevel;
		this.perChunkDensity = perChunkDensity;
		this.minVeinRadius = minVeinRadius;
		this.maxVeinRadius = maxVeinRadius;
		this.ratioWidthHeight = ratioWidthHeight;
		this.blockId = blockId;
	}

	@Override
	public int hashCode() {
		return this.blockId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ToGenerateLiquid)
		{
			return this.blockId == ((ToGenerateLiquid)obj).blockId;
		}
		return false;
	}
}
