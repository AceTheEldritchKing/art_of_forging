package net.acetheeldritchking.art_of_forging.effects.potion;

import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
                    ArtOfForging.MOD_ID);

    // Evoking Maw Potion
    public static final RegistryObject<MobEffect> EVOKING_MAW =
            MOB_EFFECTS.register("evoking_maw", EvokingMawPotionEffect::new);

    // Defuse Creeper Potion
    public static final RegistryObject<MobEffect> DEFUSE_CREEPER =
            MOB_EFFECTS.register("defuse_creeper", DefuseCreeperPotionEffect::new);

    // Devouring Potion Effect
    public static final RegistryObject<MobEffect> DEVOURING =
            MOB_EFFECTS.register("devouring", DevouringPotionEffect::new);

    public static void register(IEventBus bus)
    {
        MOB_EFFECTS.register(bus);
    }
}
