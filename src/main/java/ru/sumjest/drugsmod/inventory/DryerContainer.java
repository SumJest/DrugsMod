package ru.sumjest.drugsmod.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;
import ru.sumjest.drugsmod.handler.DryerRecipes;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

public class DryerContainer extends Container{

	private TileEntityDryer tileDryer;
	private int lastCookTime;
	private int lastDryingTime;	
	private int lastItemDryingTime;
	
	public DryerContainer(InventoryPlayer inv, TileEntityDryer tedryer) {
		this.tileDryer = tedryer;
		this.addSlotToContainer(new Slot(tedryer, 0, 56, 17));
		this.addSlotToContainer(new Slot(tedryer, 1, 56, 53));
		this.addSlotToContainer(new SlotFurnace(inv.player, tedryer, 2, 116, 35));
		int i;
		
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(inv, i, 8+i * 18, 142));
		}		
	}
	
	public void addCraftingToCrafters(ICrafting craft)
	{
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileDryer.dryerCookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileDryer.dryerDryTime);
		craft.sendProgressBarUpdate(this, 2, this.lastItemDryingTime);
	}

	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); i++)
		{
			ICrafting craft = (ICrafting)this.crafters.get(i);
			if(this.lastCookTime != this.tileDryer.dryerCookTime)
			{
				craft.sendProgressBarUpdate(this, 0, this.tileDryer.dryerCookTime);
			}
			if(this.lastDryingTime != this.tileDryer.dryerDryTime)
			{
				craft.sendProgressBarUpdate(this, 1, this.tileDryer.dryerDryTime);
			}
			if(this.lastItemDryingTime != this.tileDryer.currentDryTime)
			{
				craft.sendProgressBarUpdate(this, 2, this.tileDryer.currentDryTime);
			}
			
		}
		
		this.lastDryingTime = this.tileDryer.dryerDryTime;
		this.lastCookTime = this.tileDryer.dryerCookTime;
		this.lastItemDryingTime = this.tileDryer.currentDryTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		//System.out.println(par1+ " "+par2+" "+this.tileDryer.dryerDryTime);

		switch(par1) 
		{
			case 0:
				this.tileDryer.dryerCookTime = par2;
			case 1:
				this.tileDryer.dryerDryTime = par2;
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileDryer.isUseableByPlayer(player);
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotid){
		  ItemStack itemstack = null;
		  Slot slot = (Slot) this.inventorySlots.get(slotid);
		  if(slot != null && slot.getHasStack()){
		   ItemStack itemstack1 = slot.getStack();
		   itemstack = itemstack1.copy();
		   
		   if(slotid == 2){
		    if(!this.mergeItemStack(itemstack1, 3, 39, true)){
		     return null;
		    }
		    slot.onSlotChange(itemstack1, itemstack);
		   }else if(slotid != 1 && slotid != 0){
		    if(DryerRecipes.drying().getDryingResult(itemstack1) != null){
		     if(!this.mergeItemStack(itemstack1, 0, 1, false)){
		      return null;
		     }
		    }else if(TileEntityDryer.isItemFuel(itemstack1)){
		     if(!this.mergeItemStack(itemstack1, 1, 2, false)){
		      return null;
		       }
		    }else if(slotid >=3 && slotid < 30){
		     if(!this.mergeItemStack(itemstack1, 30, 39, false)){
		      return null;
		     }
		    }else if(slotid >= 30 && slotid < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)){
		     return null;
		    }
		   }else if(!this.mergeItemStack(itemstack1, 3, 39, false)){
		    return null;
		   }
		   if(itemstack1.stackSize == 0){
		    slot.putStack((ItemStack)null);
		   }else{
		    slot.onSlotChanged();
		   }
		   if(itemstack1.stackSize == itemstack.stackSize){
		    return null;
		   }
		   slot.onPickupFromSlot(player, itemstack1);
		  }
		  return itemstack;
		 }

}
