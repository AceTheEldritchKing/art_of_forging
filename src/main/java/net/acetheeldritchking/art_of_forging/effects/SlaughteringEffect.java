package net.acetheeldritchking.art_of_forging.effects;

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

public class SlaughteringEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(slaughteringEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, slaughteringName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue
                        (slaughteringTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (slaughteringEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.oneDecimal, StatFormat.oneDecimal)));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Bonus damage
                float level = item.getEffectLevel(heldStack, slaughteringEffect);

                // Percentage to compare
                float eff = item.getEffectEfficiency(heldStack, slaughteringEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                // Bonus damage as a percentage
                float bonusDamage = getDecimalPercentage(level, baseAmount);

                // Target health
                // Base health, constant
                final float BASE_HEALTH = target.getMaxHealth();
                // Base health, not constant
                float baseHealth = target.getHealth();
                // Health as a percentage
                double healthPercentage = (baseHealth / BASE_HEALTH) * 100;

                if (level > 0 && eff <= healthPercentage) {
                    //System.out.println("Applying bonus...");
                    event.setAmount(getExactPercentage(baseAmount, bonusDamage));
                }
            }
        }
    }
}
