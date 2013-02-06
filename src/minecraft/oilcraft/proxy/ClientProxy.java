package oilcraft.proxy;

import basiccomponents.client.gui.GuiBatteryBox;
import basiccomponents.client.gui.GuiCoalGenerator;
import basiccomponents.client.gui.GuiElectricFurnace;
import basiccomponents.common.BasicComponents;
import basiccomponents.common.tileentity.TileEntityBatteryBox;
import basiccomponents.common.tileentity.TileEntityCoalGenerator;
import basiccomponents.common.tileentity.TileEntityElectricFurnace;
import oilcraft.Oilcraft;
import oilcraft.generators.OilGenerator;
import oilcraft.generators.OilGeneratorGuiContainer;
import oilcraft.generators.OilGeneratorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit()
	{
		// Preload textures
		MinecraftForgeClient.preloadTexture(Oilcraft.BLOCK_TEXTURE);
		MinecraftForgeClient.preloadTexture(Oilcraft.ITEM_TEXTURE);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity != null)
		{
			switch (ID)
			{
				case OilGenerator.OIL_GENERATOR_GUI:
					return new OilGeneratorGuiContainer(player.inventory, ((OilGeneratorTileEntity) tileEntity));
				case OilGenerator.GAS_GENERATOR_GUI:
					//return new GuiCoalGenerator(player.inventory, ((TileEntityCoalGenerator) tileEntity));
				case OilGenerator.JET_GENERATOR_GUI:
					//return new GuiElectricFurnace(player.inventory, ((TileEntityElectricFurnace) tileEntity));
			}
		}

		return null;
	}
}
