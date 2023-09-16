package net.acetheeldritchking.art_of_forging.effects.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

public class DefuseCreeperPotionEffect extends MobEffect {
    protected DefuseCreeperPotionEffect() {
        super(MobEffectCategory.HARMFUL, 12403465);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        // Defuses Creeper
        if (pLivingEntity instanceof Creeper creeper)
        {
            creeper.setSwellDir(-1);
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
