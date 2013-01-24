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
