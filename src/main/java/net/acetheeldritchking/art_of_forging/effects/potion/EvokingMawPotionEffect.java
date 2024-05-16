package net.acetheeldritchking.art_of_forging.effects.potion;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.EvokerFangs;

public class EvokingMawPotionEffect extends MobEffect {
    public EvokingMawPotionEffect() {
        super(MobEffectCategory.HARMFUL, 12403465);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide()) {
            ServerLevel world = (ServerLevel) pLivingEntity.level();

            double x = pLivingEntity.getX();
            double y = pLivingEntity.getY();
            double z = pLivingEntity.getZ();
            float yRot = pLivingEntity.getYRot();

            for (int i = 0; i < pAmplifier; i++) {
                world.addFreshEntity(new EvokerFangs
                        (world, x, y, z, yRot, 0, null));
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 8 == 0;
    }
}
