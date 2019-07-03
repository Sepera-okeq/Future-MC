package com.herobrine.future.enchantment;

import net.minecraft.enchantment.Enchantment;

public class EnchantPiercing extends EnchantmentCrossbowBase {
    public EnchantPiercing() {
        super(Rarity.COMMON, "piercing");
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 1 + (enchantmentLevel - 1) * 10;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.MULTISHOT;
    }
}
