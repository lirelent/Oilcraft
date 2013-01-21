package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CoolantSystem extends Item
{

	public CoolantSystem(int id)
	{
		super(id);
		this.setIconIndex(3);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("coolantSystem");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE;
	}
}
