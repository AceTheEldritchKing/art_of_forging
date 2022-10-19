package net.acetheeldritchking.art_of_forging;


import net.acetheeldritchking.art_of_forging.item.DummyItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.acetheeldritchking.art_of_forging.item.ModularRelic;

public class AoFRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArtOfForging.MOD_ID);
    public static final RegistryObject<Item> DUMMY_ITEM = ITEMS.register("dummyitem", DummyItem::new);

    public static final RegistryObject<Item> ModularRelic = ITEMS.register("modular_relic", ModularRelic::new);

    // Relic Thingy because I'm bored
    public static final RegistryObject<Item> BASIC_RELIC = ITEMS.register("basic_relic",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
