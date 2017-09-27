package ru.sumjest.drugsmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class dry_Cannabis extends Item
{
	public dry_Cannabis()
	{
		this.setUnlocalizedName("dry_cannabis");
		this.setMaxDamage(64);
		this.setTextureName("drugsmod:cannabis_dry");
		this.setCreativeTab(CreativeTabs.tabMaterials);
		
	}
}
