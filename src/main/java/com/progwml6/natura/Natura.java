package com.progwml6.natura;

import mantle.pulsar.control.PulseManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.progwml6.natura.blocks.BlocksNatura;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaEvents;
import com.progwml6.natura.common.PHNatura;
import com.progwml6.natura.items.ItemsNatura;
import com.progwml6.natura.worldgen.CloudWorldgen;
import com.progwml6.natura.worldgen.CropWorldGen;

@Mod(modid = "natura", name = "Natura", version = "3.0.0", acceptedMinecraftVersions = "[1.8]", dependencies = "required-after:mantle@[0.3.1,)")
public class Natura
{
    @Instance(Natura.MOD_ID)
    public static Natura INSTANCE;

    public static final String MOD_ID = "natura";

    public static final PulseManager pulsar = new PulseManager(MOD_ID);

    /* Proxies for sides, used for graphics processing */
    @SidedProxy(clientSide = "com.progwml6.natura.client.ClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy PROXY;

    public static Logger logger = LogManager.getLogger(MOD_ID);

    public static boolean retrogen;

    private BlocksNatura blocks = new BlocksNatura();

    private ItemsNatura items = new ItemsNatura();

    private NaturaCreativeTabs tabs = new NaturaCreativeTabs();

    @EventHandler
    public void preInit(FMLPreInitializationEvent evt)
    {
        MinecraftForge.EVENT_BUS.register(new NaturaEvents());
        PHNatura.initProps(evt.getSuggestedConfigurationFile());
        pulsar.preInit(evt);

        this.blocks.preInit();
        this.items.preInit();
        this.tabs.preInit();

        PROXY.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent evt)
    {
        PROXY.init();

        GameRegistry.registerWorldGenerator(new CloudWorldgen(), 20); // TODO 1.8 Find correct weight (param 2)
        GameRegistry.registerWorldGenerator(new CropWorldGen(), 20); // TODO 1.8 Find correct weight (param 2)
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent evt)
    {
        PROXY.postInit();
    }

    public BlocksNatura getBlocks()
    {
        return Natura.INSTANCE.blocks;
    }

    public ItemsNatura getItems()
    {
        return Natura.INSTANCE.items;
    }

    public NaturaCreativeTabs getCreativeTabs()
    {
        return Natura.INSTANCE.tabs;
    }
}
