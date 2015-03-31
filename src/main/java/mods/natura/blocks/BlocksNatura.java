package mods.natura.blocks;

import mantle.client.ModelVariant;
import mods.natura.Natura;
import mods.natura.blocks.material.CloudMaterial;
import mods.natura.blocks.natural.BlockClouds;
import mods.natura.blocks.natural.BlockNaturaLog;
import mods.natura.blocks.natural.BlockNaturaPlanks;
import mods.natura.items.itemblocks.ItemBlockClouds;
import mods.natura.items.itemblocks.ItemBlockNaturaLog;
import mods.natura.items.itemblocks.ItemBlockPlanks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlocksNatura
{
	public static Material cloud = new CloudMaterial();

	public BlockClouds clouds;

	public BlockNaturaLog logs;

	public BlockNaturaPlanks planks;

	public void preInit()
	{
		this.clouds = this.registerBlock("clouds", ItemBlockClouds.class, new BlockClouds());
		this.logs = this.registerBlock("logs", ItemBlockNaturaLog.class, new BlockNaturaLog());
		this.planks = this.registerBlock("planks", ItemBlockPlanks.class, new BlockNaturaPlanks());
	}

	private <T extends Block> T registerBlock(String name, Class<? extends ItemBlock> itemblock, T block)
	{
		block.setUnlocalizedName(name);
		GameRegistry.registerBlock(block, itemblock, name);

		return block;
	}

	public void init()
	{
		if (Natura.proxy.getModels() != null)
		{
			ModelVariant models = Natura.proxy.getModels();

			models.registerItemRenderer(this.clouds, BlockClouds.CloudVariant.values());
			models.registerItemRenderer(this.logs, BlockNaturaLog.LogVariant.values());
			models.registerItemRenderer(this.planks, BlockNaturaPlanks.PlanksVariant.values());
		}
	}

}