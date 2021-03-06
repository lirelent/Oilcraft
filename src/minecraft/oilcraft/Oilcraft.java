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
package oilcraft;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import oilcraft.generators.OilGenerator;
import oilcraft.generators.OilGeneratorTileEntity;
import oilcraft.liquids.OilFlow;
import oilcraft.liquids.OilStill;
import oilcraft.liquids.worldGen.ReplaceWithLiquidWorldGen;
import oilcraft.liquids.worldGen.ToGenerateLiquid;
import oilcraft.proxy.CommonProxy;
import universalelectricity.prefab.network.PacketManager;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "OilCraft", name = "OilCraft", version = Oilcraft.VERSION)
@NetworkMod(channels = { "OilCraft" }, clientSideRequired = true, serverSideRequired = false,
			packetHandler = PacketManager.class)
public class Oilcraft {
	@SidedProxy(clientSide = "oilcraft.proxy.ClientProxy", serverSide = "oilcraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Instance("OilCraft")
	public static Oilcraft instance;

	public static final String VERSION = "0.0.1";
	
	public static final String RESOURCE_PATH = "/mods/oilcraft/";

	public static final String TEXTURE_DIRECTORY = RESOURCE_PATH + "textures/";
	public static final String LANGUAGE_PATH = RESOURCE_PATH + "languages/";
	public static final String GUI_DIRECTORY = TEXTURE_DIRECTORY + "gui/";
	public static final String BLOCK_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "blocks/";
	public static final String ITEM_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "items/";
	public static final String MODEL_TEXTURE_DIRECTORY = TEXTURE_DIRECTORY + "models/";

	public static final String TEXTURE_NAME_PREFIX = "oilcraft:";

	// Items
	public static Item voltageRegulatorItem;
	public static Item alternatorItem;
	public static Item controlPanelItem;
	public static Item engineItem;
	public static Item fuelSystemItem;
	public static Item lubricationSystemItem;
	public static Item coolantSystemItem;

	@PreInit
	public void preLoad(FMLPreInitializationEvent event) {
		NetworkRegistry.instance().registerGuiHandler(this, this.proxy);
		
		Configuration conf = new Configuration(
				new File(Loader.instance().getConfigDir(), "OilCraft.cfg"));
		
		conf.load();

		OilGenerator.makeInstance(
				conf.get("blocks", "Oil_Generator", 500).getInt(), 0);
		int stillId = conf.get("blocks", "Oil_Still", 601).getInt();
		OilStill.makeInstance(stillId, 7);
		// Minecraft makes the silly assumption that the block Id of the flow
		// version of a liquid is 1 less than the still version of that same
		// liquid. In this way it can build a connection between the two blocks
		// so they can spawn each other.
		OilFlow.makeInstance(stillId - 1, 7);

		conf.save();

		proxy.preInit();
	}

	@Init
	public void load(FMLInitializationEvent event) {
		OilGenerator oilGenerator = OilGenerator.getInstance();
		LanguageRegistry.addName(oilGenerator, "Oil Generator");
		GameRegistry.registerBlock(oilGenerator, "oilGenerator");
		GameRegistry.registerTileEntity(OilGeneratorTileEntity.class,
				"oilGenerator");

		OilStill oilStill = OilStill.getInstance();
		LanguageRegistry.addName(oilStill, "Oil Still");
		GameRegistry.registerBlock(oilStill, "oilStill");

		OilFlow oilFlow = OilFlow.getInstance();
		LanguageRegistry.addName(oilFlow, "Oil Flow");
		GameRegistry.registerBlock(oilFlow, "oilFlow");

		// liquid world gen stuff
		ReplaceWithLiquidWorldGen liquidWorldGen = new ReplaceWithLiquidWorldGen();
		ToGenerateLiquid oilGen = new ToGenerateLiquid(55, 60, 1, 5, 16, 5,
				OilStill.getInstance().blockID);
		liquidWorldGen.add(oilGen, "oil");
	}

	public void registerRecipes() {
		// TODO: Create recipes
	}

}
