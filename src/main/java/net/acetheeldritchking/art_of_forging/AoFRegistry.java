package net.acetheeldritchking.art_of_forging;

import net.acetheeldritchking.art_of_forging.item.AoFCreativeModeTab;
import net.acetheeldritchking.art_of_forging.item.DummyItem;
import net.acetheeldritchking.art_of_forging.item.custom.AncientItem;
import net.acetheeldritchking.art_of_forging.item.custom.EnigmaticConstructItem;
import net.acetheeldritchking.art_of_forging.item.custom.LifeFiberItem;
import net.acetheeldritchking.art_of_forging.item.custom.SigilOfEdenItem;
import net.acetheeldritchking.art_of_forging.item.modular.ModularArtifact;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AoFRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArtOfForging.MOD_ID);

    public static final RegistryObject<Item> DUMMY_ITEM = ITEMS.register("dummyitem", DummyItem::new);
    //               //
    // Modular Items //
    //               //
    // Modular Artifact
    public static final RegistryObject<Item> MODULAR_ARTIFACT = ITEMS.register(ModularArtifact.identifier, ModularArtifact::new);

    public static final RegistryObject<Item> CURIOUS_ARTIFACT = ITEMS.register("curious_artifact",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB)));


    //        //
    // INGOTS //
    //        //
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
    //          //
    // Life Fiber
    public static RegistryObject<Item> LIFE_FIBER = ITEMS.register("life_fiber",
            () -> new LifeFiberItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).rarity(Rarity.RARE)));

    // Fang Charm
    public static final RegistryObject<Item> FANG_CHARM = ITEMS.register("fang_charm",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).rarity(Rarity.RARE).stacksTo(1)));

    // Sigil of Eden
    public static RegistryObject<Item> SIGIL_OF_EDEN = ITEMS.register("sigil_of_eden",
            () -> new SigilOfEdenItem(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
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

    // Devil's Soul Gem
    public static RegistryObject<Item> DEVILS_SOUL_GEM = ITEMS.register("devils_soul_gem",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).stacksTo(1).fireResistant()));

    // Rending Scissor Red
    public static RegistryObject<Item> RENDING_SCISSOR_RED = ITEMS.register("rending_scissor_red",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).stacksTo(1).fireResistant()));

    // Rending Scissor Purple
    public static RegistryObject<Item> RENDING_SCISSOR_PURPLE = ITEMS.register("rending_scissor_purple",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).stacksTo(1).fireResistant()));

    // Rending Scissor Complete
    public static RegistryObject<Item> RENDING_SCISSOR_COMPLETE = ITEMS.register("rending_scissor_complete",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).stacksTo(1).fireResistant()));


    //      //
    // MISC //
    //      //
    // Nano-Insectoid
    public static RegistryObject<Item> NANO_INSECTOID = ITEMS.register("nano_insectoid",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.UNCOMMON).stacksTo(16)));

    // Encoded Canister
    public static RegistryObject<Item> ENCODED_CANISTER = ITEMS.register("encoded_canister",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.UNCOMMON).stacksTo(16)));

    // Esoteric Codex
    public static RegistryObject<Item> ESOTERIC_CODEX = ITEMS.register("esoteric_codex",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE).stacksTo(1)));

    // Mark of The Architect
    public static RegistryObject<Item> MARK_OF_THE_ARCHITECT = ITEMS.register("mark_of_the_architect",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.EPIC).stacksTo(1)));

    // Shockwave Chamber
    public static RegistryObject<Item> SHOCKWAVE_CHAMBER = ITEMS.register("shockwave_chamber",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE).stacksTo(16)));


    //           //
    // MOB DROPS //
    //           //
    // Dragon Soul
    public static RegistryObject<Item> DRAGON_SOUL = ITEMS.register("dragon_soul",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE).fireResistant()));

    // Shards of Malice
    public static RegistryObject<Item> SHARDS_OF_MALICE = ITEMS.register("shards_of_malice",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.UNCOMMON).fireResistant()));

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

    // Soul Ember
    public static RegistryObject<Item> SOUL_EMBER = ITEMS.register("soul_ember",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB)));

    // Fragment of Eden
    public static RegistryObject<Item> FRAGMENT_OF_EDEN = ITEMS.register("fragment_of_eden",
            () -> new Item(new Item.Properties().tab(AoFCreativeModeTab.AOF_TAB).
                    rarity(Rarity.RARE).stacksTo(16)));


}
