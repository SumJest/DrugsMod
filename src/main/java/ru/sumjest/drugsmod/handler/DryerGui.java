package ru.sumjest.drugsmod.handler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import ru.sumjest.drugsmod.inventory.DryerContainer;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

@SideOnly(Side.CLIENT)
public class DryerGui extends GuiContainer
{

	public DryerGui(InventoryPlayer inv, TileEntityDryer tedryer) {
		super(new DryerContainer(inv, tedryer));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		// TODO Auto-generated method stub
		
	}

}
