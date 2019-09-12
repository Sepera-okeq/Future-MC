package thedarkcolour.core.util;

import com.herobrine.future.FutureMC;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import thedarkcolour.core.block.IProjectileDispenserBehaviour;

public final class RegistryHelper {
    public static void registerDispenserBehaviour(Item item, IProjectileDispenserBehaviour behaviour) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, behaviour);
    }

    public static void registerEntity(String name, Class<? extends Entity> entity, int trackingRange, int id) {
        EntityRegistry.registerModEntity(new ResourceLocation(FutureMC.ID, name), entity, name, id, FutureMC.instance, trackingRange, 1, true);
    }
}
