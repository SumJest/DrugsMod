package ru.sumjest.drugsmod.handler;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import ru.sumjest.drugsmod.inventory.DryerContainer;
import ru.sumjest.drugsmod.lib.Strings;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

@SideOnly(Side.CLIENT)
public class DryerGui extends GuiContainer
{
	
	private static final ResourceLocation dryerGuiTextures = new ResourceLocation(Strings.MODID, "textures/gui/container/dryer.png");
	private TileEntityDryer tileDryer;
	
	public DryerGui(InventoryPlayer inv, TileEntityDryer tedryer) {
		super(new DryerContainer(inv, tedryer));
		this.tileDryer = tedryer;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String string = this.tileDryer.hasCustomInventoryName() ? this.tileDryer.getInventoryName() : I18n.format(this.tileDryer.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize/2-this.fontRendererObj.getStringWidth(string),6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 94, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(dryerGuiTextures);
		int k = (this.width - this.xSize)/2;
		int l = (this.height - this.ySize)/2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int i1;
		
		if(this.tileDryer.isDrying())
		{
			i1 = this.tileDryer.getDryingTimeRemainingScaled(12);
			this.drawTexturedModalRect(k+56, l+50-i1, 176, 12-i1, 14, i1+1);
		}
		
		i1 = this.tileDryer.getCookProgressScaled(24);
		this.drawTexturedModalRect(k+79, l+34, 176, 14, i1+1, 16);
	}
	

}
