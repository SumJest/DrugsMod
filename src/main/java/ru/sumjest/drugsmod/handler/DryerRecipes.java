package ru.sumjest.drugsmod.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DryerRecipes 
{
	
	private static final DryerRecipes DRYING_BASE = new DryerRecipes();
	
	private Map dryingList = new HashMap();
	private Map experienceList = new HashMap();
	
	public static DryerRecipes drying()
	{
		return DRYING_BASE;
	}
	
	private DryerRecipes()
	{
		
	}
	
	public void addRecipie(Item item, ItemStack itemstack, float experience, int count)
	{
		this.addLists(item, itemstack, experience, count);
	}
	
	public void addLists(Item item, ItemStack itemstack, float experience, int count)
	{
		this.putLists(new ItemStack(item, count, 32767), itemstack, experience);
	}
	
	public void putLists(ItemStack itemstack, ItemStack itemstack2, float experience)
	{
		this.dryingList.put(itemstack, itemstack2);
		this.experienceList.put(itemstack, Float.valueOf(experience));
	}
	
	public ItemStack getDryingResult(ItemStack itemstack)
	{
		Iterator iterator = this.dryingList.entrySet().iterator();
		Entry entry;
		
		do
		{
			if(!iterator.hasNext())
			{
				return null;
			}
			entry = (Entry) iterator.next();
		}while(!canBeDrying(itemstack, (ItemStack)entry.getKey()));
		return (ItemStack) entry.getValue();
		
	}
	public int getDryingCost(ItemStack itemstack)
	{
		Iterator iterator = this.dryingList.entrySet().iterator();
		Entry entry;
		
		do
		{
			if(!iterator.hasNext())
			{
				return 0;
			}
			entry = (Entry) iterator.next();
		}while(!canBeDrying(itemstack, (ItemStack)entry.getKey()));
		return ((ItemStack) entry.getKey()).stackSize;
		
	}

	private boolean canBeDrying(ItemStack itemstack, ItemStack itemstack2)
	{
		return itemstack2.getItem() == itemstack.getItem() && (itemstack2.getItemDamage() == 32767 || itemstack2.getItemDamage() == itemstack.getItemDamage());
	}
	
	public float giveExperience(ItemStack itemstack)
	{
		Iterator iterator = this.experienceList.entrySet().iterator();
		Entry entry;
		
		do
		{
			if(!iterator.hasNext())
			{
				return 0.0f;
			}
			entry = (Entry) iterator.next();
		}while(!canBeDrying(itemstack, (ItemStack)entry.getKey()));
		
		if(itemstack.getItem().getSmeltingExperience(itemstack) != -1)
		{
			return itemstack.getItem().getSmeltingExperience(itemstack);
		}
		return ((Float) entry.getValue()).floatValue();
	}
}
