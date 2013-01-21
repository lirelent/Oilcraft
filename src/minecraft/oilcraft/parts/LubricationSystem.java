package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class LubricationSystem extends Item
{

	public LubricationSystem(int id)
	{
		super(id);
		this.setIconIndex(6);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("lubricationSystem");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE;
	}
}
