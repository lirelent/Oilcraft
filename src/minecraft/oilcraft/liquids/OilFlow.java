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
