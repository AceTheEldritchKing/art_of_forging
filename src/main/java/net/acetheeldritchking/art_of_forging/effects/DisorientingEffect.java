package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
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

public class DisorientingEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(disorientingEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, disorientingName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterMultiValue
                        (disorientingTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (disorientingEffect, 1.0D)), StatsHelper.withFormat
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

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Level of effect
                int level = item.getEffectLevel(heldStack, disorientingEffect);
                // Duration of effect
                int eff = (int) item.getEffectEfficiency(heldStack, disorientingEffect);

                // Apply potion effects
                if (level > 0 && !MobType.UNDEAD.equals(target.getMobType()))
                {
                    if (target instanceof Player)
                    {
                        applyEffects(level, eff, target);
                        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, eff*20,
                                level, true, true, true));
                    }
                    else if (target instanceof Creeper creeper)
                    {
                        applyEffects(level, eff, creeper);
                        // Stop creeper from exploding
                        // 5X the duration of effect to actually stop the creeper from exploding
                        creeper.addEffect(new MobEffectInstance(PotionEffects.DEFUSE_CREEPER.get(), eff*100,
                                2, false, false, false));
                    }
                    else
                    {
                        applyEffects(level, eff, target);
                    }
                }
            }
        }
    }

    private void applyEffects(int level, int duration, LivingEntity target)
    {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration*20,
                level, true, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration*20,
                level, true, true, true));
    }
}
