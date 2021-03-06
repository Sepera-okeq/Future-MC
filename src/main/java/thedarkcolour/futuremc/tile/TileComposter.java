package thedarkcolour.futuremc.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import thedarkcolour.core.inventory.DarkInventory;
import thedarkcolour.core.tile.InteractionTile;
import thedarkcolour.futuremc.block.BlockComposter;
import thedarkcolour.futuremc.init.FBlocks;
import thedarkcolour.futuremc.init.Sounds;

import javax.annotation.Nonnull;

public class TileComposter extends InteractionTile {
    private final DarkInventory inventory = new DarkInventory(1) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return BlockComposter.Companion.canCompost(stack, world.getBlockState(pos));
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public void onContentsChanged(int slot) {
            TileComposter.this.handleStack(get(slot));
        }

        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if(!isItemValid(0, stack)) {
                return stack;
            }
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if(isBoneMeal(get(0)) && !simulate) {
                world.setBlockState(pos, FBlocks.INSTANCE.getCOMPOSTER().getDefaultState());
            }
            return super.extractItem(0, amount, simulate);
        }
    };

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("buffer")) {
            inventory.deserializeNBT(compound);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("buffer", inventory.serializeNBT());
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean activated(IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return super.activated(state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void broken(IBlockState state, EntityPlayer playerIn) {
        if (!world.isRemote && world.getBlockState(pos).getValue(BlockComposter.Companion.getLEVEL()) == 8) {
            EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.6D, pos.getZ() + 0.5D);
            item.setItem(new ItemStack(Items.DYE, 1, 15));
            world.spawnEntity(item);
        }
    }

    public static boolean isBoneMeal(ItemStack stack) {
        return stack.getItem() == Items.DYE && stack.getItemDamage() == 15;
    }

    public void addItem(ItemStack stack, boolean shrink) {
        inventory.insertItem(0, stack, false);

        if(shrink) {
            stack.shrink(1);
        }
    }

    private void handleStack(ItemStack stack) {
        if (BlockComposter.Companion.canCompost(stack, world.getBlockState(pos))) {
            if (!stack.isEmpty() && !isBoneMeal(stack)) {
                if (world.rand.nextInt(100) <= BlockComposter.ItemsForComposter.getChance(stack)) {
                    addLayer();
                } else {
                    world.playSound(null, pos, Sounds.INSTANCE.getCOMPOSTER_FILL(), SoundCategory.BLOCKS, 1F, 1F);
                }
                consume();
            }
        }
    }

    public void addLayer() {
        world.setBlockState(pos, FBlocks.INSTANCE.getCOMPOSTER().getDefaultState().withProperty(BlockComposter.Companion.getLEVEL(), world.getBlockState(pos).getValue(BlockComposter.Companion.getLEVEL()) + 1));
        world.playSound(null, pos, Sounds.INSTANCE.getCOMPOSTER_FILL_SUCCESS(), SoundCategory.BLOCKS, 1F, 1F);

        if (world.getBlockState(pos).getValue(BlockComposter.Companion.getLEVEL()) >= 7) {
            world.scheduleBlockUpdate(pos, FBlocks.INSTANCE.getCOMPOSTER(), 30, 1);
        }
    }

    public void consume() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void extractBoneMeal() {
        EntityItem item = new EntityItem(world, pos.getX() + 0.5D, pos.getY() + 0.6D, pos.getZ() + 0.5D);
        item.setItem(new ItemStack(Items.DYE, 1, 15));
        world.spawnEntity(item);
        world.setBlockState(pos, FBlocks.INSTANCE.getCOMPOSTER().getDefaultState());
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        world.playSound(null, pos, Sounds.INSTANCE.getCOMPOSTER_EMPTY(), SoundCategory.BLOCKS, 1F, 1F);
    }

    public DarkInventory getInventory() {
        return inventory;
    }
}