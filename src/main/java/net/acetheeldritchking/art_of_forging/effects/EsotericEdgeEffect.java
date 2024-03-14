package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class EsotericEdgeEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(esotericEdgeEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, esotericEdgeName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterInteger
                        (esotericEdgeTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    // This does the magic damage
    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Magic Damage bonus
                float level = item.getEffectLevel(heldStack, esotericEdgeEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                // Bonus damage as a percentage
                float magicBonusDamage = getDecimalPercentage(level, baseAmount);

                if (level > 0) {
                    target.hurt(target.damageSources().magic(), magicBonusDamage);
                }
            }
        }
    }

    // This does the normal damage
    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        // LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Magic Damage bonus
                float level = item.getEffectLevel(heldStack, esotericEdgeEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                // Bonus damage as a percentage
                // float magicBonusDamage = baseAmount * (level/100);

                if (level > 0) {
                    event.setAmount(baseAmount);
                }
            }
        }
    }
}
