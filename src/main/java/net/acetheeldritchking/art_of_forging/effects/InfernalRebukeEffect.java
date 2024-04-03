package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class InfernalRebukeEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(infernalRebukeEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, infernalRebukeName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.decimalLabel, new TooltipGetterMultiValue
                        (infernalRebukeTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (infernalRebukeEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal)));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = target.getMainHandItem();
            ItemStack offHeldStack = target.getOffhandItem();

            // Main hand
            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Seconds on fire
                int level = item.getEffectLevel(heldStack, infernalRebukeEffect);

                // Chance to proc
                float eff = item.getEffectEfficiency(heldStack, infernalRebukeEffect);

                // Getting target coords
                double xTarget = (int) target.getX();
                double zTarget = (int) target.getZ();
                // Getting attacker coords
                int xAttacker = (int) attacker.getX();
                int zAttacker = (int) attacker.getZ();

                // Gotta normalize the knockback
                Vec3 vec3 = new Vec3(xTarget, 0, zTarget).subtract(xAttacker, 0, zAttacker).normalize();

                if (level > 0 && eff > (attacker.getRandom().nextFloat()*100))
                {
                    attacker.knockback(0.4, vec3.x, vec3.z);
                    attacker.setSecondsOnFire(level);

                    // Play Sound
                    target.level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 0.5F, 1.0F);
                }
            }
        }
    }
}
