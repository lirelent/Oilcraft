/**
 * 
 */
package oilcraft.liquids;

import java.util.Random;

import oilcraft.Oilcraft;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.liquids.IBlockLiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author lirelent
 *
 */
public class OilFlow extends BlockFlowing implements IBlockLiquid {
	private static OilFlow instance; 
	
    int numAdjacentSources = 0;
    int[] flowCost = new int[4];
    boolean[] isOptimalFlowDirection = new boolean[4];

    private OilFlow(int blockId)
    {
            super(blockId, Material.lava);
            this.disableStats();
            this.setHardness(100);
    }
    
    public static void makeInstance(int blockId)
    {
    	instance = new OilFlow(blockId);
    }
    
    public static OilFlow getInstance()
    {
    	return instance;
    }

    @Override
    public String getTextureFile()
    {
            return Oilcraft.BLOCK_TEXTURE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
            return 0x000000;
    }

    @Override
    public int tickRate()
    {
            return 20;
    }

    @Override
    public int stillLiquidId()
    {
            return OilStill.getInstance().blockID;
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
            return 3;
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

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
                    Random par5Random)
    {
            int var6 = this.getFlowDecay(par1World, par2, par3, par4);
            byte var7 = 1;

            if (this.blockMaterial == Material.lava
                            && !par1World.provider.isHellWorld)
            {
                    var7 = 2;
            }

            boolean var8 = true;
            int var10;

            if (var6 > 0)
            {
                    byte var9 = -100;
                    this.numAdjacentSources = 0;
                    int var12 = this.getSmallestFlowDecay(par1World, par2 - 1, par3,
                                    par4, var9);
                    var12 = this.getSmallestFlowDecay(par1World, par2 + 1, par3, par4,
                                    var12);
                    var12 = this.getSmallestFlowDecay(par1World, par2, par3, par4 - 1,
                                    var12);
                    var12 = this.getSmallestFlowDecay(par1World, par2, par3, par4 + 1,
                                    var12);
                    var10 = var12 + var7;

                    if (var10 >= 8 || var12 < 0)
                    {
                            var10 = -1;
                    }

                    if (this.getFlowDecay(par1World, par2, par3 + 1, par4) >= 0)
                    {
                            int var11 = this.getFlowDecay(par1World, par2, par3 + 1, par4);

                            if (var11 >= 8)
                            {
                                    var10 = var11;
                            }
                            else
                            {
                                    var10 = var11 + 8;
                            }
                    }

                    if (this.numAdjacentSources >= 2
                                    && this.blockMaterial == Material.water)
                    {
                            if (par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid())
                            {
                                    var10 = 0;
                            }
                            else if (par1World.getBlockMaterial(par2, par3 - 1, par4) == this.blockMaterial
                                            && par1World.getBlockMetadata(par2, par3, par4) == 0)
                            {
                                    var10 = 0;
                            }
                    }

                    if (this.blockMaterial == Material.lava && var6 < 8 && var10 < 8
                                    && var10 > var6 && par5Random.nextInt(4) != 0)
                    {
                            var10 = var6;
                            var8 = false;
                    }

                    if (var10 == var6)
                    {
                            if (var8)
                            {
                                    this.updateFlow(par1World, par2, par3, par4);
                            }
                    }
                    else
                    {
                            var6 = var10;

                            if (var10 < 0)
                            {
                                    par1World.setBlockWithNotify(par2, par3, par4, 0);
                            }
                            else
                            {
                                    par1World.setBlockMetadataWithNotify(par2, par3, par4,
                                                    var10);
                                    par1World.scheduleBlockUpdate(par2, par3, par4,
                                                    this.blockID, this.tickRate());
                                    par1World.notifyBlocksOfNeighborChange(par2, par3, par4,
                                                    this.blockID);
                            }
                    }
            }
            else
            {
                    this.updateFlow(par1World, par2, par3, par4);
            }

            if (this.liquidCanDisplaceBlock(par1World, par2, par3 - 1, par4))
            {
                    if (this.blockMaterial == Material.lava
                                    && par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water)
                    {
                            par1World.setBlockWithNotify(par2, par3 - 1, par4,
                                            Block.glass.blockID);
                            this.triggerLavaMixEffects(par1World, par2, par3 - 1, par4);
                            return;
                    }

                    if (var6 >= 8)
                    {
                            this.flowIntoBlock(par1World, par2, par3 - 1, par4, var6);
                    }
                    else
                    {
                            this.flowIntoBlock(par1World, par2, par3 - 1, par4, var6 + 8);
                    }
            }
            else if (var6 >= 0
                            && (var6 == 0 || this.blockBlocksFlow(par1World, par2,
                                            par3 - 1, par4)))
            {
                    boolean[] var13 = this.getOptimalFlowDirections(par1World, par2,
                                    par3, par4);
                    var10 = var6 + var7;

                    if (var6 >= 8)
                    {
                            var10 = 1;
                    }

                    if (var10 >= 8)
                    {
                            return;
                    }

                    if (var13[0])
                    {
                            this.flowIntoBlock(par1World, par2 - 1, par3, par4, var10);
                    }

                    if (var13[1])
                    {
                            this.flowIntoBlock(par1World, par2 + 1, par3, par4, var10);
                    }

                    if (var13[2])
                    {
                            this.flowIntoBlock(par1World, par2, par3, par4 - 1, var10);
                    }

                    if (var13[3])
                    {
                            this.flowIntoBlock(par1World, par2, par3, par4 + 1, var10);
                    }
            }
    }

    private void updateFlow(World par1World, int par2, int par3, int par4)
    {
            int var5 = par1World.getBlockMetadata(par2, par3, par4);
            par1World.setBlockAndMetadata(par2, par3, par4, this.blockID + 1, var5);
            par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3,
                            par4);
    }

    private void flowIntoBlock(World par1World, int par2, int par3, int par4,
                    int par5)
    {
            if (this.liquidCanDisplaceBlock(par1World, par2, par3, par4))
            {
                    int var6 = par1World.getBlockId(par2, par3, par4);

                    if (var6 > 0)
                    {
                            if (this.blockMaterial == Material.lava)
                            {
                                    this.triggerLavaMixEffects(par1World, par2, par3, par4);
                            }
                            else
                            {
                                    Block.blocksList[var6].dropBlockAsItem(par1World, par2,
                                                    par3, par4,
                                                    par1World.getBlockMetadata(par2, par3, par4), 0);
                            }
                    }

                    par1World.setBlockAndMetadataWithNotify(par2, par3, par4,
                                    this.blockID, par5);
            }
    }

    private boolean liquidCanDisplaceBlock(World par1World, int par2, int par3,
                    int par4)
    {
            Material var5 = par1World.getBlockMaterial(par2, par3, par4);
            return var5 == this.blockMaterial ? false
                            : (var5 == Material.lava ? false : !this.blockBlocksFlow(
                                            par1World, par2, par3, par4));
    }

    private boolean blockBlocksFlow(World par1World, int par2, int par3,
                    int par4)
    {
            int var5 = par1World.getBlockId(par2, par3, par4);

            if (var5 != Block.doorWood.blockID && var5 != Block.doorSteel.blockID
                            && var5 != Block.signPost.blockID
                            && var5 != Block.ladder.blockID && var5 != Block.reed.blockID)
            {
                    if (var5 == 0)
                    {
                            return false;
                    }
                    else
                    {
                            Material var6 = Block.blocksList[var5].blockMaterial;
                            return var6 == Material.portal ? true : var6.blocksMovement();
                    }
            }
            else
            {
                    return true;
            }
    }

    private boolean[] getOptimalFlowDirections(World par1World, int par2,
                    int par3, int par4)
    {
            int var5;
            int var6;

            for (var5 = 0; var5 < 4; ++var5)
            {
                    this.flowCost[var5] = 1000;
                    var6 = par2;
                    int var8 = par4;

                    if (var5 == 0)
                    {
                            var6 = par2 - 1;
                    }

                    if (var5 == 1)
                    {
                            ++var6;
                    }

                    if (var5 == 2)
                    {
                            var8 = par4 - 1;
                    }

                    if (var5 == 3)
                    {
                            ++var8;
                    }

                    if (!this.blockBlocksFlow(par1World, var6, par3, var8)
                                    && (par1World.getBlockMaterial(var6, par3, var8) != this.blockMaterial || par1World
                                                    .getBlockMetadata(var6, par3, var8) != 0))
                    {
                            if (this.blockBlocksFlow(par1World, var6, par3 - 1, var8))
                            {
                                    this.flowCost[var5] = this.calculateFlowCost(par1World,
                                                    var6, par3, var8, 1, var5);
                            }
                            else
                            {
                                    this.flowCost[var5] = 0;
                            }
                    }
            }

            var5 = this.flowCost[0];

            for (var6 = 1; var6 < 4; ++var6)
            {
                    if (this.flowCost[var6] < var5)
                    {
                            var5 = this.flowCost[var6];
                    }
            }

            for (var6 = 0; var6 < 4; ++var6)
            {
                    this.isOptimalFlowDirection[var6] = this.flowCost[var6] == var5;
            }

            return this.isOptimalFlowDirection;
    }

    private int calculateFlowCost(World par1World, int par2, int par3,
                    int par4, int par5, int par6)
    {
            int var7 = 1000;

            for (int var8 = 0; var8 < 4; ++var8)
            {
                    if ((var8 != 0 || par6 != 1) && (var8 != 1 || par6 != 0)
                                    && (var8 != 2 || par6 != 3) && (var8 != 3 || par6 != 2))
                    {
                            int var9 = par2;
                            int var11 = par4;

                            if (var8 == 0)
                            {
                                    var9 = par2 - 1;
                            }

                            if (var8 == 1)
                            {
                                    ++var9;
                            }

                            if (var8 == 2)
                            {
                                    var11 = par4 - 1;
                            }

                            if (var8 == 3)
                            {
                                    ++var11;
                            }

                            if (!this.blockBlocksFlow(par1World, var9, par3, var11)
                                            && (par1World.getBlockMaterial(var9, par3, var11) != this.blockMaterial || par1World
                                                            .getBlockMetadata(var9, par3, var11) != 0))
                            {
                                    if (!this.blockBlocksFlow(par1World, var9, par3 - 1, var11))
                                    {
                                            return par5;
                                    }

                                    if (par5 < 4)
                                    {
                                            int var12 = this.calculateFlowCost(par1World, var9,
                                                            par3, var11, par5 + 1, var8);

                                            if (var12 < var7)
                                            {
                                                    var7 = var12;
                                            }
                                    }
                            }
                    }
            }

            return var7;
    }
}
