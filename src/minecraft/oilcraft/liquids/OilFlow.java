/**
 * 
 */
package oilcraft.liquids;

import java.util.Random;

import cpw.mods.fml.common.FMLLog;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;
import oilcraft.Oilcraft;

/**
 * @author lirelent
 *
 */
public class OilFlow extends BlockFlowing implements ILiquid {
	private static OilFlow instance; 
	
    int numAdjacentSources = 0;
    int[] flowCost = new int[4];
    boolean[] isOptimalFlowDirection = new boolean[4];

    private OilFlow(int blockId, int textureIndex)
    {
            super(blockId, Material.water);
            this.disableStats();
            this.setHardness(100.0F);
            this.setBurnProperties(blockId, 50, 80);
            this.setBlockName("oil");
            this.setRequiresSelfNotify();
            this.setLightOpacity(255);
            this.blockIndexInTexture = textureIndex;
    }
    
    public static void makeInstance(int blockId, int textureIndex)
    {
    	instance = new OilFlow(blockId, textureIndex);
    }
    
    public static OilFlow getInstance()
    {
    	return instance;
    }

	@Override
	public String getTextureFile() {
		return Oilcraft.BLOCK_TEXTURE;
	}

	@Override
	public int stillLiquidId() {
		return this.blockID;
	}

	@Override
	public boolean isMetaSensitive() {
		return false;
	}

	@Override
	public int stillLiquidMeta() {
		return 0;
	}
}
