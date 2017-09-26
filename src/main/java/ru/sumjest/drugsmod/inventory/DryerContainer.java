package ru.sumjest.drugsmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

public class DryerContainer extends Container{

	public DryerContainer(InventoryPlayer inv, TileEntityDryer tedryer) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

}
