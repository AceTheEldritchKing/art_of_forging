package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class DragonMistEffect {

    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(dragonMist, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, dragonMistName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterMultiValue
                        (dragonMistTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (dragonMist, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal)));

        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event)
    {
        LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();
            ItemStack offHeldStack = attacker.getOffhandItem();

            // Main hand
            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Duration of mist
                int level = item.getEffectLevel(heldStack, dragonMist);
                // Percentage chance to summon mist
                float eff = item.getEffectEfficiency(heldStack, dragonMist);

                if (level > 0 && !attacker.level.isClientSide()
                    && eff > (target.getRandom().nextFloat() * 100))
                {
                    ServerLevel world = (ServerLevel) attacker.level;

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
                    aoeCloud.setRadiusPerTick((5.0F - aoeCloud.getRadius()) / (float)aoeCloud.getDuration());
                    aoeCloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));

                    // Summon mist
                    world.addFreshEntity(aoeCloud);
                }
            }
        }
    }
}
