package net.acetheeldritchking.art_of_forging;

import com.mojang.logging.LogUtils;
import net.acetheeldritchking.art_of_forging.effects.*;
import net.acetheeldritchking.art_of_forging.effects.curio.*;
import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.acetheeldritchking.art_of_forging.item.AoFCreativeModeTab;
import net.acetheeldritchking.art_of_forging.item.ScrollHelper;
import net.acetheeldritchking.art_of_forging.loot.ModLootModifiers;
import net.acetheeldritchking.art_of_forging.networking.AoFPackets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.TetraRegistries;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArtOfForging.MOD_ID)
public class ArtOfForging {
    public static final String MOD_ID = "art_of_forging";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ArtOfForging() {
        // IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        var bus = FMLJavaModLoadingContext.get().getModEventBus();


        // Register the commonSetup method for modloading
        // modEventBus.addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        // Curios
        bus.addListener(this::enqueueIMC);

        // Items //
        AoFRegistry.ITEMS.register(bus);

        // Creative Tab //
        AoFCreativeModeTab.register(bus);

        // Loot Tables //
        ModLootModifiers.register(bus);

        // Potion Effects //
        PotionEffects.register(bus);

        // Item Effects //
        // Withering
        MinecraftForge.EVENT_BUS.register(new WitheringEffect());
        // Storm Caller
        MinecraftForge.EVENT_BUS.register(new StormCallerEffect());
        // Evoking Maw
        MinecraftForge.EVENT_BUS.register(new EvokingMawEffect());
        // Life Steal
        MinecraftForge.EVENT_BUS.register(new LifeStealEffect());
        // Knockback
        MinecraftForge.EVENT_BUS.register(new KnockbackEffect());
        // Life Fiber Loss
        MinecraftForge.EVENT_BUS.register(new LifeFiberLossEffect());
        // Cavalry
        MinecraftForge.EVENT_BUS.register(new CavalryEffect());
        // Dismounting
        MinecraftForge.EVENT_BUS.register(new DismountingEffect());
        // Dragon Mist
        MinecraftForge.EVENT_BUS.register(new DragonMistEffect());
        // Disorienting
        MinecraftForge.EVENT_BUS.register(new DisorientingEffect());
        // Vengeance
        MinecraftForge.EVENT_BUS.register(new VengeanceEffect());
        // Resolve
        MinecraftForge.EVENT_BUS.register(new ResolveEffect());
        // Devouring
        MinecraftForge.EVENT_BUS.register(new DevouringEffect());
        // Infernal Rebuke
        MinecraftForge.EVENT_BUS.register(new InfernalRebukeEffect());
        // Hubris
        MinecraftForge.EVENT_BUS.register(new HubrisEffect());
        // Slaughtering
        MinecraftForge.EVENT_BUS.register(new SlaughteringEffect());
        // Carnage
        MinecraftForge.EVENT_BUS.register(new CarnageEffect());
        // Judgement
        MinecraftForge.EVENT_BUS.register(new JudgementEffect());
        // Retribution
        MinecraftForge.EVENT_BUS.register(new RetributionEffect());
        // Esoteric Edge
        MinecraftForge.EVENT_BUS.register(new EsotericEdgeEffect());
        // Feasting
        MinecraftForge.EVENT_BUS.register(new FeastingEffect());
        // Nano-Fused
        MinecraftForge.EVENT_BUS.register(new NanoFusedEffect());
        // Beheading
        MinecraftForge.EVENT_BUS.register(new BeheadingEffect());
        // Soul Charged
        MinecraftForge.EVENT_BUS.register(new SoulChargedEffect());
        // Sonic Shock
        MinecraftForge.EVENT_BUS.register(new SonicShockEffect());
        // Conquering
        MinecraftForge.EVENT_BUS.register(new ConqueringEffect());
        // Subjugation
        MinecraftForge.EVENT_BUS.register(new SubjugationEffect());
        // Goliath Slayer
        MinecraftForge.EVENT_BUS.register(new GoliathSlayerEffect());

        // Curio Effects //
        // Flame Protection
        MinecraftForge.EVENT_BUS.register(new CurioFireResistanceEffect());
        // Arcane Protection
        MinecraftForge.EVENT_BUS.register(new CurioMagicResistanceEffect());
        // Strength Infused
        MinecraftForge.EVENT_BUS.register(new CurioStrengthEffect());
        // Healing Infused
        MinecraftForge.EVENT_BUS.register(new CurioRegenerationEffect());
        // Karma Infused
        MinecraftForge.EVENT_BUS.register(new CurioKarmaEffect());
        // Haste Infused
        MinecraftForge.EVENT_BUS.register(new CurioHasteEffect());
        // Third Sight
        MinecraftForge.EVENT_BUS.register(new CurioGlowingEffect());

        // Fix for DummyItem
        bus.addListener(this::buildCreativeTab);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Packets let's go!
        event.enqueueWork(() -> {
            AoFPackets.register();
        });
    }

    // Curios Compat
    // I looked at how Artifacts did it
    public void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder().size(1).build());
    }

    public void buildCreativeTab(final BuildCreativeModeTabContentsEvent event) {
        // This method is called for every registered Creative Tab added to Minecraft, so we only want to run this code
        // whenever it's called for the Tetra creative tab (which is registered under "tetra:default"
        if (event.getTabKey().location().equals(new ResourceLocation("tetra:default"))) {
            LOGGER.info("Registering AoF schematics with Tetra creative tab");
            event.accept(this.setupSchematic("tetra/flamberge_blade", "art_of_forging", new String[]{ "sword/flamberge_blade" }, false, 2, 16750098, 6, 15, 4, 7));
            event.accept(this.setupSchematic("single/head/halberd_head/halberd_head", "art_of_forging",new String[]{"single/head/halberd_head/halberd_head"}, false, 1, 4475647, 8, 1, 4, 5));
            event.accept(this.setupSchematic("sword/key_guard", "art_of_forging",new String[]{"sword/key_guard"}, false, 1, 16442377, 0, 1, 9, 4));
            event.accept(this.setupSchematic("single/head/mace_head/mace_head", "art_of_forging",new String[]{"single/head/mace_head/mace_head"}, false, 1, 5636192, 9, 3, 6, 2));
            event.accept(this.setupSchematic("tetra/crucible_blade", "art_of_forging",new String[]{"sword/crucible_blade"}, false, 2, 16719360, 8, 7, 9, 2));
            event.accept(this.setupSchematic("bow/stave/dreadnought_stave", "art_of_forging",new String[]{"bow/stave/dreadnought_stave", "bow/stave/dreadnought_cross_stave"}, false, 1, 15971103, 8, 1, 9, 5));
            event.accept(this.setupSchematic("sword/katana/katana_blade", "art_of_forging",new String[]{"sword/katana/katana_blade", "sword/tsuba_guard"}, false, 2, 14417680, 5, 10, 13, 2));
            event.accept(this.setupSchematic("bow/string/compound_string", "art_of_forging",new String[]{"bow/string/compound_string", "crossbow/string/compound_cross_string"}, false, 1, 1697160, 15, 13, 12, 14));
            event.accept(this.setupSchematic("utilize/hammer", "art_of_forging",new String[]{"utilize/hammer"}, false, 2, 16422889, 1, 15, 12, 8));
            event.accept(this.setupSchematic("sword/katana/murasama_blade", "otherworldly",new String[]{"sword/katana/murasama_blade"}, false, 2, 12919587, 6, 7, 13, 15));
            event.accept(this.setupSchematic("tetra/rending_scissor_complete", "otherworldly",new String[]{"sword/scissor_blade_left", "sword/scissor_blade_right", "sword/rending_scissor_complete"}, false, 2, 14885250, 1, 15, 2, 13));
            event.accept(this.setupSchematic("sword/crucible/architects_crucible_blade", "true_crucible",new String[]{"sword/crucible_blade", "sword/crucible/architects_crucible_blade"}, true, 2, 16711746, 8, 7, 9, 2));
            event.accept(this.setupSchematic("sword/tonal_blade", "art_of_forging",new String[]{"sword/tonal_blade"}, false, 1, 14350246, 3, 5, 6, 9));
            event.accept(this.setupSchematic("sword/thousand_cold_nights", "otherworldly",new String[]{"sword/katana/murasama_blade", "sword/thousand_cold_nights"}, true, 2, 6061184, 7, 8, 14, 13));
        }
    }

    // TODO: Move to it's own file?
    private static final RegistryObject<Item> tetraScroll = RegistryObject.create(new ResourceLocation("tetra:scroll_rolled"), TetraRegistries.items.getRegistryName(), TetraMod.MOD_ID);
    private ItemStack setupSchematic(String key, String details, String[] schematics, boolean isIntricate, int material, int tint, Integer... glyphs) {
        ScrollHelper data = new ScrollHelper(key, Optional.ofNullable(details), isIntricate, material, tint, Arrays.asList(glyphs),
                Arrays.stream(schematics).map((s) -> new ResourceLocation(TetraMod.MOD_ID, s)).collect(Collectors.toList()), Collections.emptyList());
        ItemStack itemStack = new ItemStack(tetraScroll.get());
        data.write(itemStack);
        return itemStack;
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            WitheringEffect.init();
            StormCallerEffect.init();
            EvokingMawEffect.init();
            LifeStealEffect.init();
            KnockbackEffect.init();
            LifeFiberLossEffect.init();
            CavalryEffect.init();
            DismountingEffect.init();
            DragonMistEffect.init();
            DisorientingEffect.init();
            VengeanceEffect.init();
            ResolveEffect.init();
            DevouringEffect.init();
            InfernalRebukeEffect.init();
            CarnageEffect.init();
            SlaughteringEffect.init();
            HubrisEffect.init();
            JudgementEffect.init();
            RetributionEffect.init();
            EsotericEdgeEffect.init();
            FeastingEffect.init();
            NanoFusedEffect.init();
            BeheadingEffect.init();
            SoulChargedEffect.init();
            SonicShockEffect.init();
            ConqueringEffect.init();
            SubjugationEffect.init();
            GoliathSlayerEffect.init();
            // Curio
            CurioFireResistanceEffect.init();
            CurioMagicResistanceEffect.init();
            CurioStrengthEffect.init();
            CurioRegenerationEffect.init();
            CurioKarmaEffect.init();
            CurioHasteEffect.init();
            CurioGlowingEffect.init();
        }
    }
}
