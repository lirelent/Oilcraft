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
/**
 * 
 */
package oilcraft;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;

/**
 * We need our own creative tab, oh and is a singleton class.
 * 
 * @author lirelent
 */
public class OilCreativeTab extends CreativeTabs {
	private static OilCreativeTab instance;

	private OilCreativeTab(String label) {
		super(label);
		LanguageRegistry.instance().addStringLocalization("itemGroup.OilCraft",
				"en_US", "OilCraft");
	}

	public static OilCreativeTab getInstance() {
		if (instance == null) {
			instance = new OilCreativeTab("OilCraft");
		}
		return instance;
	}
}
