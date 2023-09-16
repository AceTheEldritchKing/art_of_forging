package net.acetheeldritchking.art_of_forging;

import net.acetheeldritchking.art_of_forging.item.AoFCreativeModeTab;
import net.acetheeldritchking.art_of_forging.item.DummyItem;
import net.acetheeldritchking.art_of_forging.item.custom.AncientItem;
import net.acetheeldritchking.art_of_forging.item.custom.EnigmaticConstructItem;
import net.acetheeldritchking.art_of_forging.item.custom.LifeFiberItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AoFRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArtOfForging.MOD_ID);

    public static final RegistryObject<Item> DUMMY_ITEM = ITEMS.register("dummyitem", DummyItem::new);

    //        //
    // INGOTS //
    // Resonant Alloy
    public static final RegistryObject<Item> RESONANT_ALLOY = ITEMS.register("resonant_alloy",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).fireResistant()));

    // Forged Steel
    public static final RegistryObject<Item> FORGED_STEEL_INGOT = ITEMS.register("forged_steel_ingot",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).fireResistant()));

    // Vobrite Crystal
    public static RegistryObject<Item> VOBRITE_CRYSTAL = ITEMS.register("vobrite_crystal",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).rarity(Rarity.UNCOMMON).
                    fireResistant()));

    // Vobrivium Ingot
    public static RegistryObject<Item> VOBRIVIUM_INGOT = ITEMS.register("vobrivium_ingot",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).rarity(Rarity.UNCOMMON).
                    fireResistant()));

    // Endsteel
    public static RegistryObject<Item> ENDSTEEL_INGOT = ITEMS.register("endsteel_ingot",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.UNCOMMON).fireResistant()));


    //          //
    // TREASURE //
    // Life Fiber
    public static RegistryObject<Item> LIFE_FIBER = ITEMS.register("life_fiber",
            () -> new LifeFiberItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).rarity(Rarity.RARE)));

    // Fang Charm
    public static final RegistryObject<Item> FANG_CHARM = ITEMS.register("fang_charm",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).rarity(Rarity.RARE).stacksTo(1)));

    // Sigil of Eden
    public static RegistryObject<Item> SIGIL_OF_EDEN = ITEMS.register("sigil_of_eden",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).stacksTo(1).fireResistant()));

    // Enigmatic Construct
    public static RegistryObject<Item> ENIGMATIC_CONSTRUCT = ITEMS.register("enigmatic_construct",
            () -> new EnigmaticConstructItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Ancient Axe head
    public static RegistryObject<Item> ANCIENT_AXE = ITEMS.register("ancient_axe",
            () -> new AncientItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Ancient Blade
    public static RegistryObject<Item> ANCIENT_BLADE = ITEMS.register("ancient_blade",
            () -> new AncientItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Ancient Flail
    public static RegistryObject<Item> ANCIENT_FLAIL = ITEMS.register("ancient_flail",
            () -> new AncientItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Demonic Axe Head
    public static RegistryObject<Item> DEMONIC_AXE = ITEMS.register("demonic_axe",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Demonic Blade
    public static RegistryObject<Item> DEMONIC_BLADE = ITEMS.register("demonic_blade",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Demonic Flail
    public static RegistryObject<Item> DEMONIC_FLAIL = ITEMS.register("demonic_flail",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).fireResistant().stacksTo(1)));

    // Encoded Manuscript

    // Esoteric Codex

    // Mark of The Architect


    //      //
    // MISC //
    //      //
    // Nano-Insectoid
    public static RegistryObject<Item> NANO_INSECTOID = ITEMS.register("nano_insectoid",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.UNCOMMON).stacksTo(16)));


    //           //
    // MOB DROPS //
    // Dragon Soul
    public static RegistryObject<Item> DRAGON_SOUL = ITEMS.register("dragon_soul",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE).fireResistant()));

    // Shards of Malice
    public static RegistryObject<Item> SHARDS_OF_MALICE = ITEMS.register("shards_of_malice",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.UNCOMMON).fireResistant().stacksTo(16)));

    // Potent Mixture
    public static RegistryObject<Item> POTENT_MIXTURE = ITEMS.register("potent_mixture",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE)));

    // Heart of Ender
    public static RegistryObject<Item> HEART_OF_ENDER = ITEMS.register("heart_of_ender",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE)));

    // Eerie Shard
    public static RegistryObject<Item> EERIE_SHARD = ITEMS.register("eerie_shard",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE).stacksTo(16)));

}
