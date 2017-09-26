package ru.sumjest.drugsmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Cannabis extends Item
{
	public Cannabis()
	{
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setTextureName("drugsmod:cannabis");
		this.setUnlocalizedName("cannabis");
	}
	
}
