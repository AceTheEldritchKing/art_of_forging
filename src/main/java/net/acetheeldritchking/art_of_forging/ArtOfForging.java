package net.acetheeldritchking.art_of_forging;

import com.mojang.logging.LogUtils;
import net.acetheeldritchking.art_of_forging.effects.*;
import net.acetheeldritchking.art_of_forging.effects.curio.*;
import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.acetheeldritchking.art_of_forging.loot.ModLootModifiers;
import net.acetheeldritchking.art_of_forging.networking.AoFPackets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

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
