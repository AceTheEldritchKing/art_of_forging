package net.acetheeldritchking.art_of_forging.effects.potion;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DevouringPotionEffect extends MobEffect {
    public DevouringPotionEffect() {
        super(MobEffectCategory.HARMFUL, 13041721);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.hurt(DamageSource.MAGIC, pAmplifier);

        if (!pLivingEntity.level.isClientSide)
        {
            ServerLevel world = (ServerLevel) pLivingEntity.level;

            world.sendParticles(new DustColorTransitionOptions
                            (new Vector3f(0.5F, 0.18F, 0.18F), new Vector3f(0.95F, 0.1F, 0.1F), 1.5F),
                    pLivingEntity.getX(), pLivingEntity.getY(0.5), pLivingEntity.getZ(), 25, 0.5D, 0.3D, 0.5D, 0.0D);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 10 == 0;
    }
}
