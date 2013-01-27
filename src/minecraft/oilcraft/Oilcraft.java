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
import oilcraft.parts.Alternator;
import oilcraft.parts.ControlPanel;
import oilcraft.parts.CoolantSystem;
import oilcraft.parts.Engine;
import oilcraft.parts.FuelSystem;
import oilcraft.parts.LubricationSystem;
import oilcraft.parts.VoltageRegulator;
import oilcraft.proxy.CommonProxy;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "OilCraft", name = "OilCraft", version = Oilcraft.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Oilcraft {
	@SidedProxy(clientSide = "oilcraft.proxy.ClientProxy", serverSide = "oilcraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final String VERSION = "0.0.1";
	public static final String TEXTURE_PATH = "/oilcraft/textures/";
	public static final String BLOCK_TEXTURE = TEXTURE_PATH + "blocks.png";
	public static final String ITEM_TEXTURE = TEXTURE_PATH + "items.png";

	public static final Configuration CONFIGURATION = new Configuration(
			new File(Loader.instance().getConfigDir(), "OilCraft.cfg"));

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
		CONFIGURATION.load();

		voltageRegulatorItem = new VoltageRegulator(CONFIGURATION.get("items",
				"Voltage_Regulator", 8000).getInt());
		alternatorItem = new Alternator(CONFIGURATION.get("items",
				"Alternator", 8001).getInt());
		controlPanelItem = new ControlPanel(CONFIGURATION.get("items",
				"Control_Panel", 8002).getInt());
		engineItem = new Engine(CONFIGURATION.get("items", "Engine", 8003)
				.getInt());
		fuelSystemItem = new FuelSystem(CONFIGURATION.get("items",
				"Fuel_System", 8004).getInt());
		lubricationSystemItem = new LubricationSystem(CONFIGURATION.get(
				"items", "Lubcrication_System", 8005).getInt());
		coolantSystemItem = new CoolantSystem(CONFIGURATION.get("items",
				"Coolant_System", 8006).getInt());

		OilGenerator.makeInstance(
				CONFIGURATION.get("blocks", "Oil_Generator", 500).getInt(), 0);
		int stillId = CONFIGURATION.get("blocks", "Oil_Still", 601).getInt();
		OilStill.makeInstance(stillId, 7);
		// Minecraft makes the silly assumption that the block Id of the flow
		// version of a liquid is 1 less than the still version of that same
		// liquid. In this way it can build a connection between the two blocks
		// so they can spawn each other.
		OilFlow.makeInstance(stillId - 1, 7);

		CONFIGURATION.save();
		
		proxy.preInit();
	}

	@Init
	public void load(FMLInitializationEvent event) {
		LanguageRegistry.addName(voltageRegulatorItem, "Voltage Regulator");
		LanguageRegistry.addName(alternatorItem, "Alternator");
		LanguageRegistry.addName(controlPanelItem, "Control Panel");
		LanguageRegistry.addName(engineItem, "Engine");
		LanguageRegistry.addName(fuelSystemItem, "Fuel System");
		LanguageRegistry.addName(lubricationSystemItem, "Lubrication System");
		LanguageRegistry.addName(coolantSystemItem, "Coolant System");

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
