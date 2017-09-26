package ru.sumjest.drugsmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.sumjest.drugsmod.blocks.CannabisScrub;
import ru.sumjest.drugsmod.blocks.Dryer;
import ru.sumjest.drugsmod.items.Cannabis;
import ru.sumjest.drugsmod.items.dry_Cannabis;
import ru.sumjest.drugsmod.items.seeds_Cannabis;

@Mod (modid = "drugsmod", name="Drugs Mod", version = "0.1")
public class DrugsMod 
{
	public static Block dryer;
	public static Block cannabis_scrub;
	public static Item cannabis;
	public static Item dry_cannabis;
	public static Item seeds_cannabis;
	
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		dryer = new Dryer();
		cannabis = new Cannabis();
		dry_cannabis = new dry_Cannabis();	
		cannabis_scrub = new CannabisScrub();
		seeds_cannabis = new ItemSeeds(cannabis_scrub, Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("cannabis_seed").setTextureName("drugsmod:seeds_cannabis");
		
		DMHooks.mainRegistry();
		GameRegistry.registerItem(cannabis, cannabis.getUnlocalizedName());
		GameRegistry.registerItem(dry_cannabis, dry_cannabis.getUnlocalizedName());
		GameRegistry.registerItem(seeds_cannabis, seeds_cannabis.getUnlocalizedName());
		GameRegistry.registerBlock(dryer, dryer.getUnlocalizedName());
		GameRegistry.registerBlock(cannabis_scrub, cannabis_scrub.getUnlocalizedName());
		GameRegistry.addRecipe(new ItemStack(DrugsMod.dryer, 1), new Object[]{
				"I#I",
				"I#I",
				"III",
				('I'), Items.iron_ingot, ('#'), Blocks.iron_bars
		});
	}
}
