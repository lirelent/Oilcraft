package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Engine extends Item
{

	public Engine(int id)
	{
		super(id);
		this.setIconIndex(4);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("Engine");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE_PATH;
	}
}
