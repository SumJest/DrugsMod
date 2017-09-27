package ru.sumjest.drugsmod.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public void addRecipie(ItemStack item, ItemStack itemstack, float experience)
	{
		this.addLists(item, itemstack, experience);
	}
	
	public void addLists(ItemStack item, ItemStack itemstack, float experience)
	{
		this.putLists(item, itemstack, experience);
	}
	
	public void putLists(ItemStack item, ItemStack itemstack, float experience)
	{
		this.dryingList.put(item, itemstack);
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
	
	public ItemStack getDryingCost(ItemStack itemstack)
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
		}while(!itemstack.getItem().equals(((ItemStack)entry.getKey()).getItem()));
		return (ItemStack) entry.getKey();
	}
	
	private boolean canBeDrying(ItemStack itemstack, ItemStack itemstack2)
	{
		return itemstack2.getItem() == itemstack.getItem() && (itemstack2.getItemDamage() == 32767 || itemstack2.getItemDamage() == itemstack.getItemDamage()) && itemstack.stackSize >= itemstack2.stackSize;
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
