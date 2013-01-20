package oilcraft.proxy;

import oilcraft.Oilcraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{

	@Override
	public void registerRenderers()
	{
		MinecraftForgeClient.preloadTexture(Oilcraft.BLOCK_TEXTURE_PATH);
		MinecraftForgeClient.preloadTexture(Oilcraft.ITEM_TEXTURE_PATH);
	}

}
