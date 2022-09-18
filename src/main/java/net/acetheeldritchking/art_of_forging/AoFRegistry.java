package net.acetheeldritchking.art_of_forging;


import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AoFRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArtOfForging.MOD_ID);
    public static final RegistryObject<Item> DUMMY_ITEM = ITEMS.register("dummyitem", DummyItem::new);
}
