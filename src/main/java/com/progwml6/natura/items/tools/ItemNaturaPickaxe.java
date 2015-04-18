package com.progwml6.natura.items.tools;

import com.progwml6.natura.creativetabs.NaturaCreativeTabs;
import net.minecraft.item.ItemPickaxe;

public class ItemNaturaPickaxe extends ItemPickaxe
{
    public ItemNaturaPickaxe(ToolMaterial material)
    {
        super(material);
        this.setCreativeTab(NaturaCreativeTabs.tab);
    }
}
