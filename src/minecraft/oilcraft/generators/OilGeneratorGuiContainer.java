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
package oilcraft.generators;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import oilcraft.Oilcraft;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLLog;

public class OilGeneratorGuiContainer extends GuiContainer
{

    public OilGeneratorGuiContainer(InventoryPlayer IP,
	    OilGeneratorTileEntity tileEntity)
    {
	super(new OilGeneratorContainer(IP, tileEntity));
    }

    protected void drawGuiContainerForegroundLayer()
    {
	// draw text and stuff here
	// the parameters for drawString are: string, x, y, color
	fontRenderer.drawString("Oil Generator", 8, 6, 4210752);
	// draws "Inventory" or your regional equivalent
	fontRenderer.drawString(
		StatCollector.translateToLocal("container.inventory"), 8,
		ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1,
	    int par2,
	    int par3)
    {
	// draw your Gui here, only thing you need to change is the path
	int texture = mc.renderEngine.getTexture(Oilcraft.TEXTURE_PATH
		+ "OilGenerator.png");
	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	this.mc.renderEngine.bindTexture(texture);
	int x = (width - xSize) / 2;
	int y = (height - ySize) / 2;
	this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

	/*
	 * top of tank is 4 px down and bottom is 72 px down
	 * giving a height of 68
	 */
	int emptyPixels = (int) (68 * (1 - 0.25));
	//this.drawTexturedModalRect(x + 5, y + 4, 177, 1, 26, 3 + fillPixels);
	this.drawTexturedModalRect(x + 5, y + 8 + emptyPixels, 177, 4 + emptyPixels, 26, 68 - emptyPixels);
    }

    public Object getServerGuiElement(int id,
	    EntityPlayer player,
	    World world,
	    int x,
	    int y,
	    int z)
    {
	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
	if (tileEntity instanceof OilGeneratorTileEntity)
	{
	    return new OilGeneratorContainer(player.inventory,
		    (OilGeneratorTileEntity) tileEntity);
	}
	return null;
    }

    public Object getClientGuiElement(int id,
	    EntityPlayer player,
	    World world,
	    int x,
	    int y,
	    int z)
    {
	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
	if (tileEntity instanceof OilGeneratorTileEntity)
	{
	    return new OilGeneratorGuiContainer(player.inventory,
		    (OilGeneratorTileEntity) tileEntity);
	}
	return null;
    }

}
