package oilcraft.parts;

import oilcraft.Oilcraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ControlPanel extends Item
{

	public ControlPanel(int id)
	{
		super(id);
		this.setIconIndex(2);
		this.setMaxStackSize(8);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setItemName("controlPanel");
	}

	@Override
	public String getTextureFile()
	{
		return Oilcraft.ITEM_TEXTURE_PATH;
	}
}
