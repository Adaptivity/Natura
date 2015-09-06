package com.progwml6.natura.common.items.util;

import java.util.List;

import com.progwml6.natura.common.Natura;
import com.progwml6.natura.common.util.NaturaCreativeTabs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNaturaMaterial extends ItemNaturaCrafting
{
    public ItemNaturaMaterial()
    {
        super(new String[] { "barley.plant", "barley.flour", "wheat.flour", "cotton.plant", "powder.sulfur", "fletching.ghostwood", "leather.imp", "string.flame", "dye.blue" });
        this.setCreativeTab(NaturaCreativeTabs.tabMisc);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append("item.").append(unlocalizedNames[itemstack.getItemDamage()]).toString();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return ("" + StatCollector.translateToLocal("natura." + this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
        case 0:
            list.add(StatCollector.translateToLocal("tooltip.barley"));
            break;
        case 1:
        case 2:
            list.add(StatCollector.translateToLocal("tooltip.flour1"));
            list.add(StatCollector.translateToLocal("tooltip.flour2"));
            break;
        case 3:
            list.add(StatCollector.translateToLocal("tooltip.cotton"));
            break;
        case 4:
            list.add(StatCollector.translateToLocal("tooltip.sulfur"));
            break;
        case 5:
            list.add(StatCollector.translateToLocal("tooltip.fletching"));
            break;
        case 6:
            list.add(StatCollector.translateToLocal("tooltip.imp"));
            break;
        case 7:
            list.add(StatCollector.translateToLocal("tooltip.string"));
            break;
        }
    }

}
