package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class FuelSystem extends Item
{

	public FuelSystem(int id)
	{
		super(id);
		this.setIconIndex(5);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("fuelSystem");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE;
	}
}
