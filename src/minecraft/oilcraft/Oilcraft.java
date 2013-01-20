package oilcraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import oilcraft.generators.OilGenerator;
import oilcraft.generators.OilGeneratorTileEntity;
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

@Mod(modid = "OilCraft", version = Oilcraft.VERSION, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Oilcraft
{
	@SidedProxy(clientSide = "oilcraft.proxy.ClientProxy", serverSide = "oilcraft.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static final String VERSION = "0.0.1";
	public static final String TEXTURE_PATH = "/oilcraft/textures/";
	public static final String BLOCK_TEXTURE_PATH = TEXTURE_PATH + "blocks.png";
	public static final String ITEM_TEXTURE_PATH = TEXTURE_PATH + "items.png";

	public static final Configuration CONFIGURATION = new Configuration(Loader.instance().getConfigDir(), true);

	// Items
	public static Item voltageRegulatorItem;
	public static Item alternatorItem;
	public static Item controlPanelItem;
	public static Item engineItem;
	public static Item fuelSystemItem;
	public static Item lubricationSystemItem;
	public static Item coolantSystemItem;

	// Blocks
	public static Block oilGenerator;

	@PreInit
	public void preLoad(FMLPreInitializationEvent event)
	{
		CONFIGURATION.load();

		voltageRegulatorItem = new VoltageRegulator(CONFIGURATION.get("items", "Voltage_Regulator", 8000).getInt());
		alternatorItem = new Alternator(CONFIGURATION.get("items", "Alternator", 8001).getInt());
		controlPanelItem = new ControlPanel(CONFIGURATION.get("items", "Control_Panel", 8002).getInt());
		engineItem = new Engine(CONFIGURATION.get("items", "Engine", 8003).getInt());
		fuelSystemItem = new FuelSystem(CONFIGURATION.get("items", "Fuel_System", 8004).getInt());
		lubricationSystemItem = new LubricationSystem(CONFIGURATION.get("items", "Lubcrication_System", 8005).getInt());
		coolantSystemItem = new CoolantSystem(CONFIGURATION.get("items", "Coolant_System", 8006).getInt());

		oilGenerator = new OilGenerator(CONFIGURATION.get("blocks", "Oil_Generator", 500).getInt());

		CONFIGURATION.save();
	}

	@Init
	public void load(FMLInitializationEvent event)
	{
		proxy.registerRenderers();

		LanguageRegistry.addName(voltageRegulatorItem, "Voltage Regulator");
		LanguageRegistry.addName(alternatorItem, "Alternator");
		LanguageRegistry.addName(controlPanelItem, "Control Panel");
		LanguageRegistry.addName(engineItem, "Engine");
		LanguageRegistry.addName(fuelSystemItem, "Fuel System");
		LanguageRegistry.addName(lubricationSystemItem, "Lubrication System");
		LanguageRegistry.addName(coolantSystemItem, "Coolant System");

		GameRegistry.registerTileEntity(OilGeneratorTileEntity.class, "oilGenerator");

		LanguageRegistry.addName(oilGenerator, "Oil Generator");
	}

	public void registerRecipes()
	{
		// TODO: Create recipes
	}

}
