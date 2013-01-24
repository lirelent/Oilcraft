package oilcraft.proxy;

import basiccomponents.common.BasicComponents;
import oilcraft.Oilcraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit()
	{
		// Preload textures
		MinecraftForgeClient.preloadTexture(Oilcraft.BLOCK_TEXTURE);
		MinecraftForgeClient.preloadTexture(Oilcraft.ITEM_TEXTURE);
	}

}
