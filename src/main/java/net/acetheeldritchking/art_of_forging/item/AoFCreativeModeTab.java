package net.acetheeldritchking.art_of_forging.item;

import net.acetheeldritchking.art_of_forging.AoFRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AoFCreativeModeTab {
    public static final CreativeModeTab AOF_TAB = new CreativeModeTab
            ("art_of_forging_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AoFRegistry.FORGED_STEEL_INGOT.get());
        }
    };
}
