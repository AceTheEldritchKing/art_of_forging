package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class DismountingEffect {

    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(dismountingEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                dismountingEffectName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (dismountingEffectTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (dismountingEffect, 1.0D)), StatsHelper.withFormat
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
                // Amount of extra damage to mounted entity
                int level = item.getEffectLevel(heldStack, dismountingEffect);

                float baseAmount = event.getAmount();
                // Gets bonus damage as percentage
                float bonusDamage = getDecimalPercentage(level, baseAmount);

                // Chance to dismount entity
                double eff = item.getEffectEfficiency(heldStack, dismountingEffect);

                // Add bonus damage to the mounted entity
                // Also has chance to dismount entities
                if (level > 0) {
                    // If target is riding
                    if (target.isPassenger()) {
                        event.setAmount(getExactPercentage(baseAmount, bonusDamage));

                        if (eff > (target.getRandom().nextFloat() * 100)) {
                            target.stopRiding();
                        }
                    }

                    // If target has passenger
                    if (target.isVehicle()) {
                        if (eff > (target.getRandom().nextFloat() * 100)) {
                            target.ejectPassengers();
                        }
                    }
                }
            }
        }
    }
}
