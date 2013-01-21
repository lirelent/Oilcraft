package oilcraft.generators;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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

	private OilGenerator(int id) {
		super("Oil Generator", id, UniversalElectricity.machine, UETab.INSTANCE);
		this.blockIndexInTexture = 0;
	}

	public static void makeInstance(int id){
		instance = new OilGenerator(id);
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

}
