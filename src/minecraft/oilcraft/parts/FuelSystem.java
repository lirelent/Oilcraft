/*
 *  Copyright 2013 Ryan Metzger
 *
 *  This file is part of OilCraft.
 *
 *  OilCraft is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  OilCraft is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with OilCraft.  If not, see <http://www.gnu.org/licenses/>.
 */
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
