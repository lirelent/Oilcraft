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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
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

	@SideOnly(Side.CLIENT)
	private Icon[] theIcon;

	private OilStill(int blockId, int textureIndex) {
		super(blockId, Material.water);
		this.setHardness(100.0F);
		this.disableStats();
		this.setBurnProperties(blockId, 50, 80);
		this.setUnlocalizedName("oil");
		this.setLightOpacity(1);
	}

	public static void makeInstance(int blockId, int textureIndex) {
		instance = new OilStill(blockId, textureIndex);
	}

	public static OilStill getInstance() {
		return instance;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.theIcon = new Icon[] {
				par1IconRegister.registerIcon(Oilcraft.TEXTURE_NAME_PREFIX
						+ "oil"),
				par1IconRegister.registerIcon(Oilcraft.TEXTURE_NAME_PREFIX
						+ "oil_flow") };
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return par1 != 0 && par1 != 1 ? this.theIcon[1] : this.theIcon[0];
	}

	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0xFFFFFF;
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
