package ru.sumjest.drugsmod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class DMHooks {

	
	public static void mainRegistry()
	{
		forgeHooks();
		
	}
	public static void forgeHooks()
	{
		MinecraftForge.addGrassSeed(new ItemStack(DrugsMod.seeds_cannabis), 1);
	}
}
