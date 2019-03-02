package com.herobrine.future.utils.proxy;

import com.herobrine.future.FutureJava;
import com.herobrine.future.tile.GuiHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        //RenderingRegistry.registerEntityRenderingHandler(EntityTrident.class, new RenderTrident(new ModelTrident()));
    }

    @Override
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(FutureJava.instance, new GuiHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {

    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Init.initModel();
    }
}