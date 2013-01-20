package oilcraft.generators;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class OilGeneratorGuiContainer extends GuiContainer
{

	public OilGeneratorGuiContainer(InventoryPlayer IP, OilGeneratorTileEntity tileEntity)
	{
		super(new OilGeneratorContainer(IP, tileEntity));
	}

	protected void drawGuiContainerForegroundLayer()
	{
		// draw text and stuff here
		// the parameters for drawString are: string, x, y, color
		fontRenderer.drawString("Oil Generator", 8, 6, 4210752);
		// draws "Inventory" or your regional equivalent
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		// draw your Gui here, only thing you need to change is the path
		int texture = mc.renderEngine.getTexture("/your/texture/path/here.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof OilGeneratorTileEntity) { return new OilGeneratorContainer(player.inventory, (OilGeneratorTileEntity) tileEntity); }
		return null;
	}

	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof OilGeneratorTileEntity) { return new OilGeneratorGuiContainer(player.inventory, (OilGeneratorTileEntity) tileEntity); }
		return null;
	}

}
