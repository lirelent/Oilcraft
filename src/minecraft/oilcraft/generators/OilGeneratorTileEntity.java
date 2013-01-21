package oilcraft.generators;

import universalelectricity.core.implement.IConductor;
import universalelectricity.prefab.tile.TileEntityElectricityProducer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class OilGeneratorTileEntity extends TileEntityElectricityProducer implements IInventory
{
	/**
	 * Maximum amount of energy needed to generate electricity
	 */
	private static final int MAX_GENERATE_WATTS = 10000;

	/**
	 * Amount of heat the coal generator needs before generating electricity.
	 */
	private static final int MIN_GENERATE_WATTS = 100;

	private static final float BASE_ACCELERATION = 0.3f;

	/**
	 * Per second
	 */
	private double prevGenerateWatts, generateWatts = 0;

	private IConductor connectedElectricUnit = null;
	/**
	 * The number of ticks that a fresh copy of the currently-burning item would keep the furnace
	 * burning for
	 */
	private int itemCookTime = 0;
	
	private ItemStack[] inv;

	public OilGeneratorTileEntity()
	{
		inv = new ItemStack[1];
	}

	@Override
	public int getSizeInventory()
	{
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			if (stack.stackSize <= amt)
			{
				setInventorySlotContents(slot, null);
			}
			else
			{
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0)
				{
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack stack = getStackInSlot(slot);
		if (stack != null)
		{
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName()
	{
		return "Oil Generator";
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest()
	{
	}

	@Override
	public void closeChest()
	{
	}

}
