package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class SlaughteringEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(slaughteringEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                slaughteringName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (slaughteringTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (slaughteringEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal))
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

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
