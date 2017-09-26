package ru.sumjest.drugsmod.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import ru.sumjest.drugsmod.DrugsMod;

public class seeds_Cannabis extends Item implements IPlantable
{
	public seeds_Cannabis()
	{
		this.setUnlocalizedName("seeds_cannabis");
		this.setMaxStackSize(64);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.setTextureName("drugsmod:seeds_cannabis");
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return null;
	}

	@Override
	public Block getPlant(IBlockAccess world, int x, int y, int z) {
		return null;
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
		return 0;
	}
	
    public boolean onItemUse(ItemStack is, EntityPlayer enity, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if (p_77648_7_ != 1)
        {
            return false;
        }
        else if (enity.canPlayerEdit(x, y, z, p_77648_7_, is) && enity.canPlayerEdit(x, y + 1, z, p_77648_7_, is))
        {
            if (world.getBlock(x, y, z) == Blocks.farmland && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, DrugsMod.cannabis_scrub);
                --is.stackSize;
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
	
	
}
