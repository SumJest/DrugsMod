package ru.sumjest.drugsmod.tile_entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDryer extends TileEntity implements ISidedInventory
{
	
	private static final int[] slotsTop = new int[]{0};
	private static final int[] slotsBottom = new int[]{2, 1};
	private static final int[] slotsSides = new int[]{1};
	
	private ItemStack[] dryerItemStacks = new ItemStack[3];

	public int dryerDryTime;
	public int currentDryTime;
	
	public int dryerCookTime;
	
	private String dryerName;

	public void dryerName(String string)
	{
		this.dryerName = string;
	}
	
	@Override
	public int getSizeInventory() {
		return this.dryerItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return dryerItemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.dryerItemStacks[par1] != null)
		{
			ItemStack itemstack;
			if(this.dryerItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.dryerItemStacks[par1];
				this.dryerItemStacks[par1] = null;
				return itemstack;
			}else{
				itemstack = this.dryerItemStacks[par1].splitStack(par2);
				
				if(this.dryerItemStacks[par1].stackSize == 0)
				{
					 this.dryerItemStacks[par1] = null;
				}
				return itemstack;
			}
		}else{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.dryerItemStacks[slot]!=null)
		{
			ItemStack itemstack = this.dryerItemStacks[slot];
			this.dryerItemStacks[slot] = null;
			return itemstack;
		}else
		{
			return null;
		}
		
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) // LOOK AT ME 
	{
		this.dryerItemStacks[slot] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
		
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.dryerName : "Dryer";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.dryerName != null && this.dryerName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
