package com.herobrine.future.tile.advancedfurnace;

import com.herobrine.future.blocks.BlockFurnaceAdvanced;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiFurnaceAdvanced extends GuiContainer {
    private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");
    private final InventoryPlayer playerInv;
    private final TileFurnaceAdvanced te;

    public GuiFurnaceAdvanced(InventoryPlayer playerInv, TileFurnaceAdvanced te) {
        super(new ContainerFurnaceAdvanced(playerInv, te));
        this.playerInv = playerInv;
        this.te = te;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format(te.getType() == BlockFurnaceAdvanced.FurnaceType.SMOKER ? "container.Smoker" : "container.BlastFurnace");
        fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(FURNACE_GUI_TEXTURES);
        int i = (width - xSize) / 2;
        int j = (height - ySize) / 2;
        drawTexturedModalRect(i, j, 0, 0, xSize, ySize);

        if (te.isBurning()) {
            int k = getBurnLeftScaled();
            drawTexturedModalRect(i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = getCookProgressScaled();
        drawTexturedModalRect(i + 79, j + 34, 176, 14, l + 1, 16);
    }

    private int getCookProgressScaled() {
        int i = te.getField(2);
        int j = 100;
        return i != 0 ? i * 24 / j : 0;
    }

    private int getBurnLeftScaled() {
        int j = te.getField(0);
        int i = te.getField(1);
        if(i == 0) {
            i = TileEntityFurnace.getItemBurnTime(te.getStackInSlot(1));
        }
        if(i == 0) {
            i = 200;
        }

        return i != 0 && j != 0 ? j * 13 / i : 0;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}