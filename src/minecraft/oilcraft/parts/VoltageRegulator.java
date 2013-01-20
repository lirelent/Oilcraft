package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class VoltageRegulator extends Item
{

	public VoltageRegulator(int id)
	{
		super(id);
		this.setIconIndex(0);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("voltageRegulator");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE_PATH;
	}
}