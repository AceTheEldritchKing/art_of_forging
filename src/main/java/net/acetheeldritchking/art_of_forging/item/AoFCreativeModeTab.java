package net.acetheeldritchking.art_of_forging.item;

import net.acetheeldritchking.art_of_forging.AoFRegistry;
import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AoFCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArtOfForging.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("witheringboon",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AoFRegistry.FORGED_STEEL_INGOT.get()))
                    .title(Component.translatable("creativetab.art_of_forging"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(AoFRegistry.CURIOUS_ARTIFACT.get());


                        pOutput.accept(AoFRegistry.RESONANT_ALLOY.get());
                        pOutput.accept(AoFRegistry.VOBRITE_CRYSTAL.get());
                        pOutput.accept(AoFRegistry.VOBRIVIUM_INGOT.get());
                        pOutput.accept(AoFRegistry.ENDSTEEL_INGOT.get());


                        pOutput.accept(AoFRegistry.LIFE_FIBER.get());
                        pOutput.accept(AoFRegistry.FANG_CHARM.get());
                        pOutput.accept(AoFRegistry.SIGIL_OF_EDEN.get());
                        pOutput.accept(AoFRegistry.ENIGMATIC_CONSTRUCT.get());
                        pOutput.accept(AoFRegistry.ANCIENT_AXE.get());
                        pOutput.accept(AoFRegistry.ANCIENT_BLADE.get());
                        pOutput.accept(AoFRegistry.ANCIENT_FLAIL.get());
                        pOutput.accept(AoFRegistry.DEMONIC_AXE.get());
                        pOutput.accept(AoFRegistry.DEMONIC_BLADE.get());
                        pOutput.accept(AoFRegistry.DEMONIC_FLAIL.get());
                        pOutput.accept(AoFRegistry.DEVILS_SOUL_GEM.get());
                        pOutput.accept(AoFRegistry.RENDING_SCISSOR_RED.get());
                        pOutput.accept(AoFRegistry.RENDING_SCISSOR_PURPLE.get());
                        pOutput.accept(AoFRegistry.RENDING_SCISSOR_COMPLETE.get());


                        pOutput.accept(AoFRegistry.NANO_INSECTOID.get());
                        pOutput.accept(AoFRegistry.ENCODED_CANISTER.get());
                        pOutput.accept(AoFRegistry.ESOTERIC_CODEX.get());
                        pOutput.accept(AoFRegistry.MARK_OF_THE_ARCHITECT.get());
                        pOutput.accept(AoFRegistry.SHOCKWAVE_CHAMBER.get());


                        pOutput.accept(AoFRegistry.DRAGON_SOUL.get());
                        pOutput.accept(AoFRegistry.SHARDS_OF_MALICE.get());
                        pOutput.accept(AoFRegistry.POTENT_MIXTURE.get());
                        pOutput.accept(AoFRegistry.HEART_OF_ENDER.get());
                        pOutput.accept(AoFRegistry.EERIE_SHARD.get());
                        pOutput.accept(AoFRegistry.SOUL_EMBER.get());
                        pOutput.accept(AoFRegistry.FRAGMENT_OF_EDEN.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
