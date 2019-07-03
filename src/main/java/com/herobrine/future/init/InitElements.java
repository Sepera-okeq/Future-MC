package com.herobrine.future.init;

import com.herobrine.future.items.ItemBamboo;
import com.herobrine.future.tile.advancedfurnace.TileFurnaceAdvanced;
import com.herobrine.future.tile.barrel.TileBarrel;
import com.herobrine.future.tile.stonecutter.TileStonecutter;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import static com.herobrine.future.config.FutureConfig.general;
import static com.herobrine.future.config.FutureConfig.modFlowers;
import static com.herobrine.future.init.Init.*;
import static net.minecraftforge.common.BiomeDictionary.Type;

@SuppressWarnings("ConstantConditions")
@Mod.EventBusSubscriber
public class InitElements {
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();
        if(general.lantern) r.register(LANTERN);
        if(general.stonecutter) r.register(STONECUTTER);
        if(general.barrel) r.register(BARREL);
        if(general.blastFurnace) r.register(BLAST_FURNACE);
        if(general.smoker) r.register(SMOKER);
        if(general.loom) r.register(LOOM);
        if(general.fletchingTable) r.register(FLETCHING_TABLE);
        if(general.smithingTable) r.register(SMITHING_TABLE);
        if(general.grindstone) r.register(GRINDSTONE);
        //if(general.lectern) r.register(LECTERN);
        if(general.composter) r.register(COMPOSTER);

        if(modFlowers.cornflower) r.register(CORNFLOWER);
        if(modFlowers.lily) r.register(LILY_OF_VALLEY);
        if(modFlowers.witherRose) r.register(WITHER_ROSE);
        if(general.berryBush) r.register(BERRY_BUSH);
        if(general.campfire) r.register(CAMPFIRE);

        if(general.strippedLogs) r.registerAll(STRIPPED_OAK_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_BIRCH_LOG, STRIPPED_JUNGLE_LOG, STRIPPED_ACACIA_LOG, STRIPPED_DARK_OAK_LOG);
        if(general.newWallVariants) r.registerAll(BRICK_WALL, GRANITE_WALL, ANDESITE_WALL, DIORITE_WALL, SANDSTONE_WALL, RED_SANDSTONE_WALL, STONE_BRICK_WALL, MOSSY_STONE_WALL, NETHER_BRICK_WALL, RED_NETHER_BRICK_WALL, END_STONE_WALL, PRISMARINE_WALL);
        //if(general.newSlabVariants) r.registerAll(ItemNewSlab.Slabs.SLAB_HALVES.toArray(new BlockNewSlab.Half[0]));
        //if(general.newSlabVariants) r.registerAll(ItemNewSlab.Slabs.SLAB_DOUBLES.toArray(new BlockNewSlab.Double[0]));
        if(general.smoothStone) r.register(SMOOTH_STONE);
        if(general.smoothQuartz) r.register(SMOOTH_QUARTZ);
        //if(general.waterColumns) r.registerAll(BUBBLE_COLUMN, SOULSAND_OVERRIDE);
        if(general.bamboo) r.register(BAMBOO_STALK);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        if(general.lantern) r.register(makeItemBlock(LANTERN));
        if(general.stonecutter) r.register(makeItemBlock(STONECUTTER));
        if(general.barrel && !InitHelper.isCharmItemLoaded("barrel")) r.register(makeItemBlock(BARREL));
        if(general.blastFurnace) r.register(makeItemBlock(BLAST_FURNACE));
        if(general.smoker) r.register(makeItemBlock(SMOKER));
        if(general.loom) r.register(makeItemBlock(LOOM));
        if(general.fletchingTable) r.register(makeItemBlock(FLETCHING_TABLE));
        if(general.smithingTable) r.register(makeItemBlock(SMITHING_TABLE));
        if(general.grindstone) r.register(makeItemBlock(GRINDSTONE));
        if(general.composter) r.register(makeItemBlock(COMPOSTER));

        if(general.trident) r.register(TRIDENT);
        //if(general.crossbow) r.register(CROSSBOW);

        if(modFlowers.lily) r.register(makeItemBlock(LILY_OF_VALLEY));
        if(modFlowers.cornflower) r.register(makeItemBlock(CORNFLOWER));
        if(modFlowers.witherRose) r.register(makeItemBlock(WITHER_ROSE));
        if(modFlowers.suspiciousStew) r.register(SUSPICIOUS_STEW);
        if(modFlowers.dyes) r.register(DYES);
        if(general.bamboo) r.register(BAMBOO_ITEM);
        if(general.berryBush) r.register(SWEET_BERRY);
        if(general.campfire) r.register(makeItemBlock(CAMPFIRE));

        if(general.strippedLogs) r.registerAll(makeItemBlocks(STRIPPED_OAK_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_BIRCH_LOG, STRIPPED_JUNGLE_LOG, STRIPPED_ACACIA_LOG, STRIPPED_DARK_OAK_LOG));
        if(general.smoothStone) r.register(makeItemBlock(SMOOTH_STONE));
        if(general.smoothQuartz) r.register(makeItemBlock(SMOOTH_QUARTZ));
        if(general.newWallVariants) r.registerAll(makeItemBlocks(BRICK_WALL, GRANITE_WALL, ANDESITE_WALL, DIORITE_WALL, SANDSTONE_WALL, RED_SANDSTONE_WALL, STONE_BRICK_WALL, MOSSY_STONE_WALL, NETHER_BRICK_WALL, RED_NETHER_BRICK_WALL, END_STONE_WALL, PRISMARINE_WALL));
        //if(general.newSlabVariants) r.registerAll(ItemNewSlab.Slabs.SLAB_ITEMS.toArray(new ItemNewSlab[0]));

        registerTileEntities();
    }

    public static void registerTileEntities() {
        if(general.stonecutter) GameRegistry.registerTileEntity(TileStonecutter.class, new ResourceLocation(MODID + ":containerStonecutter"));
        if(general.barrel) GameRegistry.registerTileEntity(TileBarrel.class, new ResourceLocation(MODID + ":containerBarrel"));
        if(general.blastFurnace) GameRegistry.registerTileEntity(TileFurnaceAdvanced.TileBlastFurnace.class, new ResourceLocation(MODID + ":containerBlastFurnace"));
        if(general.smoker) GameRegistry.registerTileEntity(TileFurnaceAdvanced.TileSmoker.class, new ResourceLocation(MODID + ":containerSmoker"));
    }

    //@SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {

    }

    public static void registerBiome(RegistryEvent.Register<Biome> event, Biome biome, String name, BiomeManager.BiomeType type, Type... types) {
        biome.setRegistryName(name);
        event.getRegistry().register(biome);
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, 50));
        BiomeManager.addSpawnBiome(biome);
    }

    public static Item makeItemBlock(Block block) {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }

    public static Item[] makeItemBlocks(Block... blocks) {
        List<Item> list = new ArrayList<>();

        for(Block block : blocks) {
            list.add(makeItemBlock(block));
        }
        return list.toArray(new Item[0]);
    }
}