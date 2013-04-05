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
package oilcraft.generators;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import oilcraft.Oilcraft;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.block.BlockAdvanced;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Singleton version of block for oil generator
 * 
 * @author lirelent
 */
public class OilGenerator extends BlockAdvanced {
	public static final int OIL_GENERATOR_METADATA = 0;
	public static final int GAS_GENERATOR_METADATA = 4;
	public static final int JET_GENERATOR_METADATA = 8;

	public static final int OIL_GENERATOR_GUI = 0;
	public static final int GAS_GENERATOR_GUI = 1;
	public static final int JET_GENERATOR_GUI = 2;

	private static OilGenerator instance;
	private Icon iconOutput;
	private Icon iconMachineSide;
	private Icon iconOilGenerator;

	private OilGenerator(int id, int textureIndex) {
		super(id, UniversalElectricity.machine);
		this.setStepSound(soundMetalFootstep);
	}

	public static void makeInstance(int id, int textureIndex) {
		instance = new OilGenerator(id, textureIndex);
	}

	public static OilGenerator getInstance() {
		return instance;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister
				.registerIcon(Oilcraft.TEXTURE_NAME_PREFIX + "machine");
		this.iconOutput = par1IconRegister
				.registerIcon(Oilcraft.TEXTURE_NAME_PREFIX + "machine_output");

		this.iconMachineSide = par1IconRegister
				.registerIcon(Oilcraft.TEXTURE_NAME_PREFIX + "machine_side");
		this.iconOilGenerator = par1IconRegister
				.registerIcon(Oilcraft.TEXTURE_NAME_PREFIX + "oilGenerator");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new OilGeneratorTileEntity();
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (side == 0 || side == 1) {
			return this.blockIcon;
		}

		if (metadata >= JET_GENERATOR_METADATA) {
			metadata -= JET_GENERATOR_METADATA;

			// If it is the front side
			if (side == metadata + 2) {
				return this.iconOilGenerator;
			}
			// If it is the back side
			else if (side == ForgeDirection.getOrientation(metadata + 2)
					.getOpposite().ordinal()) {
				return this.iconOutput;
			}
		} else if (metadata >= GAS_GENERATOR_METADATA) {
			metadata -= GAS_GENERATOR_METADATA;

			// If it is the front side
			if (side == metadata + 2) {
				return this.iconOilGenerator;
			}
			// If it is the back side
			else if (side == ForgeDirection.getOrientation(metadata + 2)
					.getOpposite().ordinal()) {
				return this.iconOutput;
			}
		} else {
			// If it is the front side
			if (side == metadata + 2) {
				return this.iconOilGenerator;
			}
			// If it is the back side
			else if (side == ForgeDirection.getOrientation(metadata + 2)
					.getOpposite().ordinal()) {
				return this.iconOutput;
			}
		}

		return this.iconMachineSide;
	}

	@Override
	public boolean onMachineActivated(World par1World, int x, int y, int z,
			EntityPlayer par5EntityPlayer, int side, float hitX, float hitY,
			float hitZ) {
		TileEntity tileEntity = par1World.getBlockTileEntity(x, y, z);
		if (tileEntity == null || par5EntityPlayer.isSneaking()) {
			return false;
		}
		if (!par1World.isRemote) {
			par5EntityPlayer.openGui(Oilcraft.instance, OIL_GENERATOR_GUI,
					par1World, x, y, z);
		}
		return true;
	}
}
