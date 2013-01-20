package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Alternator extends Item
{
	public Alternator(int id)
	{
		super(id);
		this.setIconIndex(1);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("alternator");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE_PATH;
	}
}
