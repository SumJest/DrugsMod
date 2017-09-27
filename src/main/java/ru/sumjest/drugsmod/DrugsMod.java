package ru.sumjest.drugsmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import ru.sumjest.drugsmod.blocks.CannabisScrub;
import ru.sumjest.drugsmod.blocks.Dryer;
import ru.sumjest.drugsmod.handler.DryerRecipes;
import ru.sumjest.drugsmod.items.Cannabis;
import ru.sumjest.drugsmod.items.dry_Cannabis;
import ru.sumjest.drugsmod.lib.Strings;

@Mod (modid = "drugsmod", name="Drugs Mod", version = "0.1")
public class DrugsMod 
{
	public static Block dryer;
	public static Block cannabis_scrub;
	public static Item cannabis;
	public static Item dry_cannabis;
	public static Item seeds_cannabis;
	public static Block dryerActive;
	
	@SidedProxy(clientSide="ru.sumjest.drugsmod.ClientProxy", serverSide="ru.sumjest.drugsmod.ServerProxy")
	public static ServerProxy proxy;
	
	@Instance(Strings.MODID)
	public static DrugsMod modInstance;
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		dryer = new Dryer(false);
		cannabis = new Cannabis();
		dry_cannabis = new dry_Cannabis();	
		cannabis_scrub = new CannabisScrub();
		seeds_cannabis = new ItemSeeds(cannabis_scrub, Blocks.farmland).setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("cannabis_seed").setTextureName("drugsmod:seeds_cannabis");
		dryerActive = new Dryer(true).setBlockName("DryerActive");
		
		DryerRecipes.drying().addRecipie(this.cannabis,new ItemStack(this.dry_cannabis),1F,4);
		registerBlocks();
		registerItems();
		registerRecipes();
		registerOthers();	
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		proxy.registerNetworkStuff();
	}
	public void registerBlocks()
	{
		GameRegistry.registerBlock(dryer, dryer.getUnlocalizedName());
		GameRegistry.registerBlock(dryerActive, dryerActive.getUnlocalizedName());
		GameRegistry.registerBlock(cannabis_scrub, cannabis_scrub.getUnlocalizedName());
	}
	public void registerItems()
	{
		GameRegistry.registerItem(cannabis, cannabis.getUnlocalizedName());
		GameRegistry.registerItem(dry_cannabis, dry_cannabis.getUnlocalizedName());
		GameRegistry.registerItem(seeds_cannabis, seeds_cannabis.getUnlocalizedName());
	}
	public void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(DrugsMod.dryer, 1), new Object[]{
				"I#I",
				"I#I",
				"III",
				('I'), Items.iron_ingot, ('#'), Blocks.iron_bars
		});
	}
	public void registerOthers()
	{
		DMHooks.mainRegistry();
		proxy.registerTileEntities();

	}
}
