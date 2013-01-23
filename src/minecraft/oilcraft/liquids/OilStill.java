/**
 * 
 */
package oilcraft.liquids;

import oilcraft.Oilcraft;
import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.IBlockLiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author lirelent
 *
 */
public class OilStill extends BlockStationary implements IBlockLiquid {
	private static OilStill instance;
	
	private OilStill(int blockId)
    {
            super(blockId, Material.water);
            this.setHardness(100);
            this.disableStats();
    }
	
	public static void makeInstance(int blockId)
	{
		instance = new OilStill(blockId);
	}
	
	public static OilStill getInstance()
	{
		return instance;
	}

    @Override
    public String getTextureFile()
    {
            return Oilcraft.BLOCK_TEXTURE;
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
            return 0xFFFFFF;
    }

    @Override
    public int tickRate()
    {
            return 20;
    }

    @Override
    public int stillLiquidId()
    {
            return this.blockID;
    }

    @Override
    public boolean isMetaSensitive()
    {
            return false;
    }

    @Override
    public int stillLiquidMeta()
    {
            return 0;
    }

    @Override
    public boolean willGenerateSources()
    {
            return false;
    }

    @Override
    public int getFlowDistance()
    {
            return OilFlow.getInstance().getFlowDistance();
    }

    @Override
    public byte[] getLiquidRGB()
    {
            return null;
    }

    @Override
    public String getLiquidBlockTextureFile()
    {
            return Oilcraft.BLOCK_TEXTURE; //Is this supposed to be the animation?
    }

    @Override
    public NBTTagCompound getLiquidProperties()
    {
            return null;
    }

}
