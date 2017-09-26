package ru.sumjest.drugsmod;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy
{
	public void registerRenderThings()
	{
		
	}
	
	public int addArmor(String armor)
	{
		return RenderingRegistry.addNewArmourRendererPrefix(armor);
	}
	
}
