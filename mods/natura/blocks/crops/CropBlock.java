package mods.natura.blocks.crops;

import java.util.ArrayList;
import java.util.Random;

import mods.natura.client.CropRender;
import mods.natura.common.NaturaContent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CropBlock extends BlockFlower
{
    public CropBlock(int id)
    {
        super(id, Material.plants);
        this.setTickRandomly(true);
        float var3 = 0.5F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.25F, 0.5F + var3);
        this.setCreativeTab((CreativeTabs) null);
        this.setHardness(0.0F);
        this.setStepSound(soundGrassFootstep);
        this.disableStats();
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick (World world, int x, int y, int z, Random random)
    {
        super.updateTick(world, x, y, z, random);

        int light = world.getBlockLightValue(x, y, z);
        if (light >= 8)
        {
            int meta = world.getBlockMetadata(x, y, z);

            if (meta != 3 && meta != 8)
            {
                float grow = this.getGrowthRate(world, x, y, z, meta, light);

                if (random.nextInt((int) (60.0F / grow) + 1) == 0)
                {
                    meta++;
                    world.setBlockMetadataWithNotify(x, y, z, meta, 2);
                }
            }
        }
    }

    /**
     * Apply bonemeal to the crops.
     */
    public boolean fertilize (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta != 3 && meta != 8)
        {
            world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
            return true;
        }
        return false;
    }

    protected float getGrowthRate (World world, int x, int y, int z, int meta, int light)
    {
        float growth = 0.25f * (light - 7);
        Block soil = blocksList[world.getBlockId(x, y - 1, z)];

        if (world.canBlockSeeTheSky(x, y, z) || !requiresSun(meta))
            growth += 2f;

        if (soil.isFertile(world, x, y, z))
            growth *= 2f;

        return 1f + growth;
    }

    boolean requiresSun (int meta)
    {
        return true;
    }
    
    protected boolean canThisPlantGrowOnThisBlockID(int par1)
    {
        return par1 == Block.tilledField.blockID;
    }
    
    /* Left-click harvests berries */
    @Override
    public void onBlockClicked (World world, int x, int y, int z, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            int meta = world.getBlockMetadata(x, y, z);
            if (meta == 8)
            {
                world.setBlock(x, y, z, blockID, 6, 3);
                EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(NaturaContent.plantItem.itemID, 1, 4));
                world.spawnEntityInWorld(entityitem);
                entityitem.onCollideWithPlayer(player);
            }
        }
    }

    /* Right-click harvests berries */
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        /*if (world.isRemote)
            return false;*/

        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 8)
        {
            if (world.isRemote)
                return true;
            
            world.setBlock(x, y, z, blockID, 6, 3);
            EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, new ItemStack(NaturaContent.plantItem.itemID, 1, 3));
            world.spawnEntityInWorld(entityitem);
            entityitem.onCollideWithPlayer(player);
            return true;
        }
        return false;
    }

    /*public void harvestBlock (World world, EntityPlayer player, int x, int y, int z, int meta)
    {
        player.addStat(StatList.mineBlockStatArray[this.blockID], 1);
        player.addExhaustion(0.025F);

        if (this.canSilkHarvest(world, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player))
        {
            ItemStack itemstack = this.createStackedBlock(meta);

            if (itemstack != null)
            {
                this.dropBlockAsItem_do(world, x, y, z, itemstack);
            }
        }
        else
        {
            int fortune = EnchantmentHelper.getFortuneModifier(player);
            this.dropBlockAsItem(world, x, y, z, meta, fortune);
        }

        if (meta == 8 && !player.capabilities.isCreativeMode)
        {
            world.setBlock(x, y, z, this.blockID, 6, 3);
            System.out.println("Setblock");
        }
    }*/
    
    public float getBlockHardness(World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) > 3)
            return 0.5f;
        return this.blockHardness;
    }

    /*public void breakBlock(World world, int x, int y, int z, int blockID, int meta)
    {
    	if (meta == 8)
        {
            System.out.println("Woo~ "+meta);
        	world.setBlock(x, y, z, this.blockID, 6, 3);
        }
    }*/

    public Icon[] icons;
    public String[] textureNames = new String[] { "barley_1", "barley_2", "barley_3", "barley_4", "cotton_1", "cotton_2", "cotton_3", "cotton_4", "cotton_5" };

    public void registerIcons (IconRegister iconRegister)
    {
        this.icons = new Icon[textureNames.length];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = iconRegister.registerIcon("natura:" + textureNames[i]);
        }
    }

    @Override
    public Icon getIcon (int side, int meta)
    {
        return icons[meta];
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType ()
    {
        return CropRender.model;
    }

    public int idDropped (int meta, Random random, int fortune)
    {
        if (meta == 3 || meta == 8)
            return this.getCropItem(meta);
        return this.getSeedItem(meta);
    }

    protected int getCropItem (int meta)
    {
        return NaturaContent.plantItem.itemID;
    }

    protected int getSeedItem (int meta)
    {
        return NaturaContent.seeds.itemID;
    }

    public int damageDropped (int meta)
    {
        if (meta < 4)
            return 0;
        return 3;
    }

    public int seedDamageDropped (int meta)
    {
        if (meta < 4)
            return 0;
        return 1;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance (World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
    }

    @Override
    public ArrayList<ItemStack> getBlockDropped (World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata == 3 || metadata == 8)
        {
            int count = quantityDropped(metadata, fortune, world.rand);
            for (int i = 0; i < count; i++)
            {
                int id = idDropped(metadata, world.rand, 0);
                if (id > 0)
                {
                    ret.add(new ItemStack(id, 1, damageDropped(metadata)));
                }
            }
        }

        ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
        if (metadata % 4 >= 2 && world.rand.nextInt(4) == 0)
            ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));
        if ((metadata % 4 >= 3 || metadata == 8) && world.rand.nextInt(4) == 0)
            ret.add(new ItemStack(this.getSeedItem(metadata), 1, seedDamageDropped(metadata)));

        return ret;
    }

    /*@Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
    	if (meta % 4 == 0)
    		return 1+random.nextInt(fortune+1);
    	return random.nextInt(meta/4);
    }*/

    /**
     * Returns the ID of the items to drop on destruction.
     */

    @SideOnly(Side.CLIENT)
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked (World world, int x, int y, int z)
    {
        return this.getSeedItem(world.getBlockMetadata(x, y, z));
    }

    public int getDamageValue (World par1World, int par2, int par3, int par4)
    {
        return seedDamageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }

    @Override
    public EnumPlantType getPlantType (World world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    @Override
    public boolean canBlockStay (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z); //Wild crops can stay
        if (meta == 3 || meta == 8)
            return world.getBlockId(x, y, z) != 0;

        return super.canBlockStay(world, x, y, z);
    }

    @Override
    public int getPlantMetadata (World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta < 4)
            return 0;
        else
            return 4;
    }
}