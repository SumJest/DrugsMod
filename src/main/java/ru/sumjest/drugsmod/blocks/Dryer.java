package ru.sumjest.drugsmod.blocks;

import javax.management.modelmbean.ModelMBeanAttributeInfo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class Dryer extends Block{

	public IIcon top;
	public IIcon shell;
	public IIcon front;
	public IIcon backside;
	public IIcon bottom;
	
	
	public Dryer() {
		super(Material.rock);
		this.setBlockName("Dryer");
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHardness(10F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 0);
		this.setLightLevel(0F);
		this.setBlockTextureName("drugsmod:Dryer");
		
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		top = reg.registerIcon(this.textureName + "_top");
		front = reg.registerIcon(this.textureName + "_front");
		shell=  reg.registerIcon(this.textureName + "_shell");
		backside = reg.registerIcon(this.textureName + "_shell");
		bottom = reg.registerIcon(this.textureName + "_shell");
	}
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is)
    {
        int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int[] rotation = {2,5,3,4};
        world.setBlockMetadataWithNotify(x, y, z, rotation[dir], 3);
    }
	@Override
    public IIcon getIcon(int side, int meta) {
        if(meta==0)
        {
        	IIcon[] gIcons = {bottom, top, backside,front,shell,shell};
        	return gIcons[side];
        }
		ForgeDirection block_dir = ForgeDirection.getOrientation(meta);
        ForgeDirection dir = meta != 0 ? ForgeDirection.getOrientation(side) : ForgeDirection.WEST;
        if(dir == block_dir) return front;
        else if(dir == block_dir.getOpposite()) return backside;
        else if(dir == ForgeDirection.DOWN) return bottom;
        else if(dir == ForgeDirection.UP) return top;
        else return shell; 

    }
//	@Override
//	public IIcon getIcon(int side, int meta)
//	{

//	}
	
}
