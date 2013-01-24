package oilcraft.generators;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import oilcraft.OilCreativeTab;
import oilcraft.Oilcraft;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.prefab.BlockMachine;
import universalelectricity.prefab.UETab;

/**
 * Singleton version of block for oil generator
 * 
 * @author lirelent
 */
public class OilGenerator extends BlockMachine {
	public static final int OIL_GENERATOR_METADATA = 0;
	public static final int GAS_GENERATOR_METADATA = 4;
	public static final int JET_GENERATOR_METADATA = 8;

	private static OilGenerator instance;

	private OilGenerator(int id, int textureIndex) {
		super("Oil Generator", id, UniversalElectricity.machine, OilCreativeTab
				.getInstance());
		this.blockIndexInTexture = textureIndex;
		this.setStepSound(soundMetalFootstep);
	}

	public static void makeInstance(int id, int textureIndex) {
		instance = new OilGenerator(id, textureIndex);
	}

	public static OilGenerator getInstance() {
		return instance;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new OilGeneratorTileEntity();
	}

	@Override
	public String getTextureFile() {
		return Oilcraft.BLOCK_TEXTURE;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata)
	{
		if (side == 0 || side == 1) { return this.blockIndexInTexture; }

		if (metadata >= GAS_GENERATOR_METADATA)
		{
			metadata -= GAS_GENERATOR_METADATA;

			// If it is the front side
			if (side == metadata + 2)
			{
				return this.blockIndexInTexture + 2;
			}
			// If it is the back side
			else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal()) { return this.blockIndexInTexture + 6; }
		}
		else if (metadata >= JET_GENERATOR_METADATA)
		{
			metadata -= JET_GENERATOR_METADATA;

			// If it is the front side
			if (side == metadata + 2)
			{
				return this.blockIndexInTexture + 3;
			}
			// If it is the back side
			else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal()) { return this.blockIndexInTexture + 2; }

			return this.blockIndexInTexture + 4;
		}
		else
		{
			// If it is the front side
			if (side == metadata + 2)
			{
				return this.blockIndexInTexture + 3;
			}
			// If it is the back side
			else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal()) { return this.blockIndexInTexture + 5; }
		}

		return this.blockIndexInTexture + 1;
	}
}
