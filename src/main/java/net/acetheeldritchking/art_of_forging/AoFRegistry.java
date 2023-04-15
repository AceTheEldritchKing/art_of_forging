package net.acetheeldritchking.art_of_forging;

import net.acetheeldritchking.art_of_forging.item.DummyItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AoFRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArtOfForging.MOD_ID);

    public static final RegistryObject<Item> DUMMY_ITEM = ITEMS.register("dummyitem", DummyItem::new);

    public static final RegistryObject<Item> FORGED_STEEL_INGOT = ITEMS.register("forged_steel_ingot",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).fireResistant()));

    public static final RegistryObject<Item> RESONANT_ALLOY = ITEMS.register("resonant_alloy",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).fireResistant()));
}
