package net.acetheeldritchking.art_of_forging.effects.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MortalWoundsPotionEffect extends MobEffect {
    protected MortalWoundsPotionEffect() {
        super(MobEffectCategory.HARMFUL, 11607582);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "840880e6-93d8-11ee-b9d1-0242ac120002",
                -0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }
}
