package com.progwml6.natura.common;

import com.progwml6.natura.common.blocks.BlocksNatura;
import com.progwml6.natura.common.blocks.util.DispenserBehaviorSpawnEgg;
import com.progwml6.natura.common.items.ItemsNatura;
import com.progwml6.natura.common.network.NaturaGuiHandler;
import com.progwml6.natura.common.world.entites.BabyHeatscarSpider;
import com.progwml6.natura.common.world.entites.FusewoodArrow;
import com.progwml6.natura.common.world.entites.HeatscarSpider;
import com.progwml6.natura.common.world.entites.ImpEntity;
import com.progwml6.natura.common.world.entites.NitroCreeper;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Natura.INSTANCE, new NaturaGuiHandler());

		BlocksNatura.preInit();
		ItemsNatura.preInit();
	}

	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
	}

	public void postInit()
	{
		EntityRegistry.registerModEntity(ImpEntity.class, "Imp", 0, Natura.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(HeatscarSpider.class, "FlameSpider", 1, Natura.INSTANCE, 32, 5, true);
		EntityRegistry.registerModEntity(NitroCreeper.class, "NitroCreeper", 2, Natura.INSTANCE, 64, 5, true);
		EntityRegistry.registerModEntity(FusewoodArrow.class, "FusewoodArrow", 3, Natura.INSTANCE, 64, 3, true);
		EntityRegistry.registerModEntity(BabyHeatscarSpider.class, "FlameSpiderBaby", 4, Natura.INSTANCE, 32, 5, true);

		BiomeGenBase[] nether = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.NETHER);

		EntityRegistry.addSpawn(ImpEntity.class, 10, 8, 12, EnumCreatureType.CREATURE, nether);
		EntityRegistry.addSpawn(HeatscarSpider.class, 10, 4, 4, EnumCreatureType.MONSTER, nether);
		EntityRegistry.addSpawn(NitroCreeper.class, 8, 4, 6, EnumCreatureType.MONSTER, nether);
		EntityRegistry.addSpawn(BabyHeatscarSpider.class, 7, 4, 4, EnumCreatureType.MONSTER, nether);

		BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsNatura.spawn_egg, new DispenserBehaviorSpawnEgg());
	}

}
