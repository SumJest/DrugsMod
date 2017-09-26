package ru.sumjest.drugsmod.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.sumjest.drugsmod.inventory.DryerContainer;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

public class DrugsModGuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID==0)
		{
			TileEntityDryer tileentity = (TileEntityDryer) world.getTileEntity(x, y, z);
			return new DryerContainer(player.inventory, tileentity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID==0)
		{
			TileEntityDryer tileentity = (TileEntityDryer) world.getTileEntity(x, y, z);
			return new DryerGui(player.inventory, tileentity);
		}
		
		return null;
	}

}
