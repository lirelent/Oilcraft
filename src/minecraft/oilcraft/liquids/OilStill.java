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
package oilcraft.liquids;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;
import oilcraft.Oilcraft;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author lirelent
 * 
 */
public class OilStill extends BlockStationary implements ILiquid {
	private static OilStill instance;

	private OilStill(int blockId, int textureIndex) {
		super(blockId, Material.water);
        this.setHardness(100.0F);
		this.disableStats();
		this.setBurnProperties(blockId, 50, 80);
		this.setBlockName("oil");
        this.setRequiresSelfNotify();
        this.setLightOpacity(255);
		this.blockIndexInTexture = textureIndex;
	}

	public static void makeInstance(int blockId, int textureIndex) {
		instance = new OilStill(blockId, textureIndex);
	}

	public static OilStill getInstance() {
		return instance;
	}

	@Override
	public String getTextureFile() {
		return Oilcraft.BLOCK_TEXTURE;
	}

	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0xFFFFFF;
	}

	@Override
	public int tickRate() {
		return 20;
	}

	@Override
	public int stillLiquidId() {
		return this.blockID;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
		return true;
	}
}
