package ru.sumjest.drugsmod.blocks;

import java.util.Random;

import javax.management.modelmbean.ModelMBeanAttributeInfo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.sumjest.drugsmod.DrugsMod;
import ru.sumjest.drugsmod.tile_entity.TileEntityDryer;

public class Dryer extends Block{

	@SideOnly(Side.CLIENT)
	public IIcon top;
	@SideOnly(Side.CLIENT)
	public IIcon shell;
	@SideOnly(Side.CLIENT)
	public IIcon front;
	@SideOnly(Side.CLIENT)
	public IIcon backside;
	@SideOnly(Side.CLIENT)
	public IIcon bottom;
	
	private static boolean isActive;
	private final boolean isActive2;
	private final Random random = new Random();
	private int direction;
	
	public Dryer(boolean isDrying) {
		super(Material.iron);
		this.setBlockName("Dryer");
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHardness(10F);
		this.setResistance(10F);
		this.setHarvestLevel("pickaxe", 0);
		this.setLightLevel(0F);
		this.setBlockTextureName("drugsmod:Dryer");
		isActive2 = isDrying;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		
		top = reg.registerIcon(this.textureName + "_top");
		front = isActive2 ? reg.registerIcon(this.textureName + "_activefront") : reg.registerIcon(this.textureName + "_front");;
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
        this.direction = rotation[dir];
        if(is.hasDisplayName())
        {
        	((TileEntityDryer) world.getTileEntity(x, y, z)).dryerName(is.getDisplayName());
        	
        }
	
    }
	
	public static void updateBlockState(boolean active, World world, int x, int y, int z)
	{
		int direction = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getTileEntity(x, y, z);
		isActive = true;
		
		if(active)
		{
			world.setBlock(x, y, z, DrugsMod.dryerActive);
		}else{
			world.setBlock(x, y, z, DrugsMod.dryer);
		}
		
		isActive = false;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
		if(tile != null)
		{
			tile.validate();
			world.setTileEntity(x, y, z, tile);
		}

	}
		
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if(!isActive)		
		{
			TileEntityDryer tedryer = (TileEntityDryer) world.getTileEntity(x, y, z);
			
			if(tedryer != null)
			{
				for(int i = 0; i < tedryer.getInventorySize; i++)
				{
					ItemStack itemstack = tedryer.getStackInSlot(i);
					if(itemstack != null)
					{
						float f = this.random.nextFloat() * 0.6F + 0.1F;
						float f1 = this.random.nextFloat() * 0.6F + 0.1F;
						float f2 = this.random.nextFloat() * 0.6F + 0.1F;
						
						while(itemstack.stackSize > 0)
						{
							int j = this.random.nextInt(21) + 10;
							
							if(j > itemstack.stackSize)
							{
								j = itemstack.stackSize;
							}
							
							itemstack.stackSize-=j;
							EntityItem entityitem = new EntityItem(world, (double) ((float) x + f ), (double) ((float) y + f1), (double) ((float)z+f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
							
							if(itemstack.hasTagCompound())
							{
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
							
							float f3 = 0.025F;
							entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + 0.1F);
							entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
							world.spawnEntityInWorld(entityitem);
						}
					}
				}
				world.func_147453_f(x, y, z, block);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		if(this.isActive2)
		{
			int direct = world.getBlockMetadata(x, y, z);
			
			float xx = (float)x + 0.5F, yy = (float) y + rand.nextFloat() * 6.0F/16.0F, zz = (float) z + 0.5F, xx2 = random.nextFloat() * 0.3F, zz2 = 0.5F;
			
			
			if(direct == 4)
			{
				world.spawnParticle("smoke", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
			}else if(direct==5)
			{
				world.spawnParticle("smoke", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
			}else if(direct==3)
			{
				world.spawnParticle("smoke", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
			}else if(direct==2)
			{
				world.spawnParticle("smoke", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", (double) (xx - zz2), (double) yy, (double) (zz+xx2), 0.0F, 0.0F, 0.0F);
			}
			
				
				
				
			
		}
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
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		player.openGui(DrugsMod.modInstance, 0, world, x, y, z);
		return true;
	}
	
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemFromBlock(DrugsMod.dryer);
	}
	
	public Item getItem(World world, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(DrugsMod.dryer);
	}
	
	@SideOnly(Side.CLIENT)
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
	}
	

	
}
