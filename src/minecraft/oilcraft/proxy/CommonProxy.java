package oilcraft.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import oilcraft.generators.OilGenerator;
import oilcraft.generators.OilGeneratorContainer;
import oilcraft.generators.OilGeneratorTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	public void preInit()
	{
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
		{
			switch (ID)
			{
				case OilGenerator.OIL_GENERATOR_GUI:
					return new OilGeneratorContainer(player.inventory, ((OilGeneratorTileEntity) tileEntity));
				case OilGenerator.GAS_GENERATOR_GUI:
					//return new GuiCoalGenerator(player.inventory, ((TileEntityCoalGenerator) tileEntity));
				case OilGenerator.JET_GENERATOR_GUI:
					//return new GuiElectricFurnace(player.inventory, ((TileEntityElectricFurnace) tileEntity));
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}
}
