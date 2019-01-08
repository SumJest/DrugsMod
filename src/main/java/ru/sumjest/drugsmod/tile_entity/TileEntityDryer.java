package ru.sumjest.drugsmod.tile_entity;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ru.sumjest.drugsmod.DrugsMod;
import ru.sumjest.drugsmod.blocks.Dryer;
import ru.sumjest.drugsmod.handler.DryerRecipes;

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
	
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		NBTTagList tagList = tag.getTagList("Items", 10);
		this.dryerItemStacks = new ItemStack[this.getSizeInventory()];
		
		for(int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tagCompound1 = tagList.getCompoundTagAt(i);
			byte byte0 = tagCompound1.getByte("Slot");
			
			if(byte0 >= 0 && byte0 < this.dryerItemStacks.length)
			{
				this.dryerItemStacks[byte0] = ItemStack.loadItemStackFromNBT(tagCompound1);
			}
		}
		this.dryerDryTime = tag.getShort("BurnTime");
		this.dryerCookTime = tag.getShort("CookTime");
		this.currentDryTime = getItemDryingTime(this.dryerItemStacks[1]);
		if(tag.hasKey("CustomName", 8))
		{
			this.dryerName = tag.getString("CustomName");
		}
	}
	
	public void writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		
		tag.setShort("BurnTime", (short) this.dryerDryTime);
		tag.setShort("CookTime", (short)this.dryerCookTime);
		
		NBTTagList tagList = new NBTTagList();
		
		for(int i = 0; i < this.dryerItemStacks.length; i++)
		{
			if(this.dryerItemStacks[i] != null)
			{
				NBTTagCompound tag1 = new NBTTagCompound();
				tag1.setByte("Slot", (byte)i);
				this.dryerItemStacks[i].writeToNBT(tag1);
				tagList.appendTag(tag1);
			}
		}
		tag.setTag("Items", tagList);
		
		if(this.hasCustomInventoryName())
		{
			tag.setString("CustomName", this.dryerName);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.dryerCookTime * par1 / 200;
	}
	
	@SideOnly(Side.CLIENT)
	public int getDryingTimeRemainingScaled(int par1)
	{
	//	System.out.println();
		//System.out.println(this.currentDryTime);
		if(this.currentDryTime ==0)
		{
			this.currentDryTime = 200;
		}
		
		return this.dryerDryTime * par1 / getItemDryingTime(this.dryerItemStacks[1]);
	}
	
	public boolean isDrying()
	{
		return this.dryerDryTime > 0;
	}
	
	public void updateEntity()
	{
		boolean flag = this.dryerDryTime >0;
		boolean flag1 = false;
	//	if(System.currentTimeMillis() % 1000 ==0)System.out.println(this.dryerCookTime);
		if(this.dryerDryTime > 0)
		{
			this.dryerDryTime--;

		}

		if(!this.worldObj.isRemote)
		{
			if(this.dryerDryTime == 0 && this.canDrying())
			{
				this.currentDryTime = this.dryerDryTime = getItemDryingTime(this.dryerItemStacks[1]);
				if(this.dryerDryTime > 0)
				{
					flag1 =true;
					if(this.dryerItemStacks[1] != null)
					{
						this.dryerItemStacks[1].stackSize--;
						
						if(this.dryerItemStacks[1].stackSize ==0)
						{
							this.dryerItemStacks[1] = dryerItemStacks[1].getItem().getContainerItem(this.dryerItemStacks[1]);
						}
					}
				}
			}
			if(this.isDrying() && this.canDrying())
			{
				this.dryerCookTime++;
				if(this.dryerCookTime >= 200)
				{
					this.dryerCookTime = 0;
					this.dryItem();
					flag1 =true;
				}
			}
			else
			{
				this.dryerCookTime = 0;
				
			}
		}
		
		if(flag != this.dryerDryTime >0)
		{
			flag1 = true;
			Dryer.updateBlockState(this.dryerCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
		
		if(flag1)
		{
			this.markDirty();
		}
	}
	
	private void dryItem() {
		if(this.canDrying())
		{
			ItemStack itemstack = DryerRecipes.drying().getDryingResult(this.dryerItemStacks[0]);
			if(this.dryerItemStacks[2] == null)
			{
				this.dryerItemStacks[2] = itemstack.copy();
			}else if(this.dryerItemStacks[2].getItem() == itemstack.getItem())
			{
				this.dryerItemStacks[2].stackSize += itemstack.stackSize;
			}
		}
		
		this.dryerItemStacks[0].stackSize-=DryerRecipes.drying().getDryingCost(this.dryerItemStacks[0]);
				//DryerRecipes.drying().getDryingCost(this.dryerItemStacks[0]).stackSize;
		
		if(this.dryerItemStacks[0].stackSize <= 0)
		{
			this.dryerItemStacks[0] = null;
		}
	}

	private boolean canDrying()
	{
		if(this.dryerItemStacks[0] == null)
		{
			return false;
		}else
		{
			ItemStack itemstack = DryerRecipes.drying().getDryingResult(this.dryerItemStacks[0]);
			if(itemstack == null)
			{
				return false;
			}
			if(this.dryerItemStacks[0].stackSize < DryerRecipes.drying().getDryingCost(this.dryerItemStacks[0]))
			{
				return false;
			}
			if(this.dryerItemStacks[2] == null)
			{
				return true;
			}
			if(!this.dryerItemStacks[2].isItemEqual(itemstack)) return false;
			
			int result = dryerItemStacks[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= this.dryerItemStacks[2].getMaxStackSize();
		}
	}
	
	public static int getItemDryingTime(ItemStack itemstack)
	{
        if (itemstack == null)
        {
            return 0;
        }
        else
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)
                {
                    return 300;
                }

                if (block.getMaterial() == Material.wood)
                {
                    return 600;
                }

                if (block == Blocks.coal_block)
                {
                    return 30000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 400;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 400;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 400;
            if (item == Items.stick) return 200;
            if (item == Items.coal) return 3000;
            if (item == Items.lava_bucket) return 35000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 200;
            if (item == Items.blaze_rod) return 3500;
            if(item == DrugsMod.dry_cannabis) return 100;
            return GameRegistry.getFuelValue(itemstack)*2;
        }
	}
	
    public static boolean isItemFuel(ItemStack itemstack)
    {
    	//System.out.println(getItemDryingTime(itemstack));
        return getItemDryingTime(itemstack) > 0;
    }
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq((double) this.xCoord+ 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <=64.0D;
	}

	@Override
	public void openInventory() {	
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1==2 ? false : (par1==1 ? isItemFuel(itemstack) : true);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1==0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack itemstack, int par3) {
		return this.isItemValidForSlot(par1, itemstack);
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack itemstack, int par3) {
		return par3 != 0 || par1 != 1|| itemstack.getItem() == Items.bucket;
	}
	
}
