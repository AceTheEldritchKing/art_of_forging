package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class DragonMistEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(dragonMist, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                dragonMistName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (dragonMistTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (dragonMist, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal))
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Duration of mist
                int level = item.getEffectLevel(heldStack, dragonMist);
                // Percentage chance to summon mist
                float eff = item.getEffectEfficiency(heldStack, dragonMist);

                if (level > 0 && !attacker.level().isClientSide()
                        && eff > (target.getRandom().nextFloat() * 100)) {
                    ServerLevel world = (ServerLevel) attacker.level();

                    // Target pos
                    double posX = target.getX();
                    double posY = target.getY();
                    double posZ = target.getZ();

                    // AoE Cloud Stuff
                    AreaEffectCloud aoeCloud = new AreaEffectCloud
                            (world, posX, posY, posZ);

                    aoeCloud.setOwner(attacker);
                    aoeCloud.setParticle(ParticleTypes.DRAGON_BREATH);
                    aoeCloud.setRadius(0.5F);
                    aoeCloud.setDuration(level);
                    aoeCloud.setRadiusPerTick((5.0F - aoeCloud.getRadius()) / (float) aoeCloud.getDuration());
                    aoeCloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));

                    // Summon mist
                    world.addFreshEntity(aoeCloud);
                }
            }
        }
    }
}
