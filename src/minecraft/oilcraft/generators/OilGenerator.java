package oilcraft.generators;

import oilcraft.Oilcraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class OilGenerator extends BlockContainer
{

	public OilGenerator(int id)
	{
		// super("oilGenerator", id, Material.rock, CreativeTabs.tabBlock);
		// this.blockIndexInTexture = 0;
		super(id, Material.rock);
		this.setBlockName("oilGenerator");
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.blockIndexInTexture = 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new OilGeneratorTileEntity();
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.BLOCK_TEXTURE_PATH;
	}

}
