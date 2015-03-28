package mods.natura.blocks;

import java.util.List;

import mods.natura.client.GrassColorizerAlternate;
import mods.natura.common.NaturaTab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrassSlab extends NSlabBase
{
    public GrassSlab()
    {
        super(Material.ground);
        setHardness(0.6F);
        this.setCreativeTab(NaturaTab.tab);
    }

    @Override
    public void getSubBlocks (Item id, CreativeTabs tab, List list)
    {
        for (int iter = 0; iter < 3; iter++)
        {
            list.add(new ItemStack(id, 1, iter));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor ()
    {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerGrass.getGrassColor(d0, d1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor (IBlockState state)
    {
        /*if (par1 % 8 == 0)
            return this.getBlockColor();
        return 0xFFFFFF;*/
        double d0 = 0.5D;
        double d1 = 1.0D;
        if (this.getMetaFromState(state) == 1)
            return GrassColorizerAlternate.getBlueGrassColor(d0, 0.5D);
        if (this.getMetaFromState(state) == 2)
            return GrassColorizerAlternate.getOrangeGrassColor(1.0D, 1.0D);
        return ColorizerGrass.getGrassColor(d0, d1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier (IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int meta = this.getMetaFromState(worldIn.getBlockState(pos));

        for (int k1 = -1; k1 <= 1; ++k1)
        {
            for (int l1 = -1; l1 <= 1; ++l1)
            {
                BiomeGenBase biome = worldIn.getBiomeGenForCoords(pos.west(l1).south(k1));
                int grassColor = 0;
                if (meta == 1)
                {
                    double temp = MathHelper.clamp_float(biome.getFloatTemperature(pos), 0.0F, 1.0F);
                    double rainfall = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
                    grassColor = GrassColorizerAlternate.getBlueGrassColor(temp, rainfall);
                }
                else if (meta == 2)
                {
                    double temp = MathHelper.clamp_float(biome.getFloatTemperature(pos), 0.0F, 1.0F);
                    double rainfall = MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
                    grassColor = GrassColorizerAlternate.getOrangeGrassColor(temp, rainfall);
                }
                else
                {
                    grassColor = biome.getGrassColorAtPos(pos);
                }
                l += (grassColor & 16711680) >> 16;
                i1 += (grassColor & 65280) >> 8;
                j1 += grassColor & 255;
            }
        }
        return (l / 9 & 255) << 16 | (i1 / 9 & 255) << 8 | j1 / 9 & 255;
    }
}
