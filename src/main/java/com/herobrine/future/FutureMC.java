package com.herobrine.future;

import com.herobrine.future.client.gui.GuiHandler;
import com.herobrine.future.compat.oredict.OreDict;
import com.herobrine.future.entity.Entities;
import com.herobrine.future.entity.trident.EntityTrident;
import com.herobrine.future.init.FutureConfig;
import com.herobrine.future.init.Init;
import com.herobrine.future.init.InitElements;
import com.herobrine.future.init.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import thedarkcolour.core.util.RegistryHelper;

@Mod(
        modid = FutureMC.ID,
        name = "Future MC",
        version = "0.1.9",
        dependencies = "required-after:forge@[14.23.5.2776,)", useMetadata = true
)
public class FutureMC {
    public static final String ID = "minecraftfuture";
    public static Logger LOGGER;

    @SidedProxy(clientSide = "com.herobrine.future.init.proxy.ClientProxy", serverSide = "com.herobrine.future.init.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static FutureMC instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(FutureConfig.class);
        Entities.init();
        proxy.preInit(e);
        LOGGER = e.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
        GameRegistry.addSmelting(new ItemStack(Blocks.STONE), new ItemStack(Init.SMOOTH_STONE), 0.1F);
        GameRegistry.addSmelting(new ItemStack(Blocks.QUARTZ_BLOCK), new ItemStack(Init.SMOOTH_QUARTZ), 0.1F);

        if(FutureConfig.general.trident) {
            RegistryHelper.registerDispenserBehaviour(Init.TRIDENT, EntityTrident::new);
        }

        OreDict.registerOres();
        InitElements.registerGenerators();
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    // TODO - Check that this works
    public static CreativeTabs TAB;
}