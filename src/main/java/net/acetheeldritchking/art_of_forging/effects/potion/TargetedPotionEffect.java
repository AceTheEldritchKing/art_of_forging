package net.acetheeldritchking.art_of_forging.effects.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class TargetedPotionEffect extends MobEffect {
    // This effect is meant to do literally nothing
    protected TargetedPotionEffect() {
        super(MobEffectCategory.HARMFUL, 14737632);
    }
}
