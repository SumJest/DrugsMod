package ru.sumjest.drugsmod;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ru.sumjest.drugsmod.handler.DrugsModGuiHandler;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

public class ServerProxy 
{

	public void registerRenderThings()
	{
		
	}
	
	public int addArmor(String armor)
	{
		return 0;
	}
	
	public void registerNetworkStuff()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(DrugsMod.modInstance, new DrugsModGuiHandler());
	}
	
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityDryer.class, "drugsmod");
	}
}
