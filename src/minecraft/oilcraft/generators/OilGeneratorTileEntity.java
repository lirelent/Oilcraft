package oilcraft.generators;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;
import universalelectricity.core.implement.IConductor;
import universalelectricity.prefab.tile.TileEntityElectricityProducer;

public class OilGeneratorTileEntity extends TileEntityElectricityProducer
		implements IInventory, ITankContainer {
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
	 * The number of ticks that a fresh copy of the currently-burning item would
	 * keep the furnace burning for
	 */
	private int itemCookTime = 0;

	/**
	 * ammount of milliBuckets of internal storage
	 */
	private static final int FUEL_CAPACITY_MILLIBUCKET = 10000;
	private int fuelContentsMilliBuckets = 0;
	private ILiquidTank internalLiquidTank;

	private ItemStack[] inv;

	/**
	 * note this set once at construction and never changed because a particular
	 * kind of generator can only accept the kind of liquid it is designed for
	 */
	private final LiquidStack requiredLiquid;

	public OilGeneratorTileEntity() {
		inv = new ItemStack[1];
		this.requiredLiquid = LiquidDictionary.getLiquid("oil", 1);
		internalLiquidTank = new LiquidTank(requiredLiquid,
				FUEL_CAPACITY_MILLIBUCKET, this);
	}

	@Override
	public int getSizeInventory() {
		return inv.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inv[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			if (stack.stackSize <= amt) {
				setInventorySlotContents(slot, null);
			} else {
				stack = stack.splitStack(amt);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inv[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "Oil Generator";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5,
						zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	/**
	 * accept only oil from the top or bottom
	 */
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		boolean isValidResource = isValidLiquid(from, resource);

		int availibleCapactity = FUEL_CAPACITY_MILLIBUCKET
				- fuelContentsMilliBuckets;
		int inputAmount = resource.amount;
		if (inputAmount <= availibleCapactity) {
			if (doFill) {
				this.fuelContentsMilliBuckets += inputAmount;
			}
			return inputAmount;
		} else {
			if (doFill) {
				this.fuelContentsMilliBuckets = FUEL_CAPACITY_MILLIBUCKET;
			}
			return availibleCapactity;
		}
	}

	/**
	 * @param from
	 * @param resource
	 */
	private boolean isValidLiquid(ForgeDirection from, LiquidStack resource) {
		if (resource == null
				|| (from != ForgeDirection.UP && from != ForgeDirection.DOWN)) {
			return false;
		}

		return this.requiredLiquid.isLiquidEqual(resource);
	}

	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		// there is only 1 tank so ignore tank index and do a valid fill
		return fill(ForgeDirection.UP, resource, doFill);
	}

	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (from != ForgeDirection.UP && from != ForgeDirection.DOWN) {
			return null;
		}

		if (maxDrain > this.fuelContentsMilliBuckets) {
			int output = this.fuelContentsMilliBuckets;
			if (doDrain) {
				this.fuelContentsMilliBuckets = 0;
			}
			return new LiquidStack(this.requiredLiquid.itemID, output);
		} else {
			if (doDrain) {
				this.fuelContentsMilliBuckets -= maxDrain;
			}
			return new LiquidStack(this.requiredLiquid.itemID, maxDrain);
		}
	}

	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		// there is only 1 tank so ignore tank index and do a valid drain
		return drain(ForgeDirection.UP, maxDrain, doDrain);
	}

	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[] { getTank(direction, this.requiredLiquid) };
	}

	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		if ((direction == ForgeDirection.DOWN || direction == ForgeDirection.UP)
				&& type.isLiquidEqual(this.requiredLiquid)) {
			return this.internalLiquidTank;
		}
		return null;
	}

}
