package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterPercentage;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class HubrisEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(hubrisEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, hubrisName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterPercentage
                        (hubrisTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        // LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Extra damage
                float level = item.getEffectLevel(heldStack, hubrisEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                // Bonus damage as a percent
                float bonusDamage = getDecimalPercentage(level, baseAmount);

                // Base health, constant
                final float BASE_HEALTH = attacker.getMaxHealth();
                // Base health, not constant
                float baseHealth = attacker.getHealth();
                // Health as a percentage
                double percent = (baseHealth / BASE_HEALTH) * 100;

                if (level > 0) {
                    // Uncomment all of the loggers if you wish to test/debug
                    if (percent <= 30) {
                        //System.out.println("30 or less");
                        double totalDamage = calculateReducedDamageAmount(baseAmount, 2);
                        if (Double.isInfinite(totalDamage) || Double.isNaN(totalDamage) || totalDamage <= 0) {
                            //System.out.println("Uh oh! Amount is either NaN, Infinite, or below 0");
                            //System.out.println("Resetting damage back to base damage");
                            //System.out.println("Total Damage: " + totalDamage);
                            //System.out.println("Base amount: " + baseAmount);
                            event.setAmount(baseAmount);
                        } else {
                            //System.out.println("Base Damage: " + baseAmount);
                            //System.out.println("Bonus Amount: " + bonusDamage);
                            //System.out.println("Total Damage: " + totalDamage);
                            event.setAmount((float) totalDamage);
                        }
                    } else if (percent <= 40) {
                        //System.out.println("30 or 40");
                        double totalDamage = calculateReducedDamageAmount(baseAmount, 1.5);
                        if (Double.isInfinite(totalDamage) || Double.isNaN(totalDamage) || totalDamage <= 0) {
                            //System.out.println("Uh oh! Amount is either NaN, Infinite, or below 0");
                            //System.out.println("Resetting damage back to base damage");
                            //System.out.println("Total Damage: " + totalDamage);
                            //System.out.println("Base amount: " + baseAmount);
                            event.setAmount(baseAmount);
                        } else {
                            //System.out.println("Base Damage: " + baseAmount);
                            //System.out.println("Bonus Amount: " + bonusDamage);
                            //System.out.println("Total Damage: " + totalDamage);
                            event.setAmount((float) totalDamage);
                        }
                    } else if (percent <= 60) {
                        //System.out.println("50 or 60");
                        //System.out.println("Base Damage: " + baseAmount);
                        event.setAmount(baseAmount);
                    } else if (percent <= 70) {
                        //System.out.println("60 or 70");
                        double totalDamage = calculateBonusDamageAmount(baseAmount, bonusDamage, 1.5);
                        if (Double.isInfinite(totalDamage) || Double.isNaN(totalDamage) || totalDamage <= 0) {
                            //System.out.println("Uh oh! Amount is either NaN, Infinite, or below 0");
                            //System.out.println("Resetting damage back to base damage");
                            //System.out.println("Total Damage: " + totalDamage);
                            //System.out.println("Base amount: " + baseAmount);
                            event.setAmount(baseAmount);
                        } else {
                            //System.out.println("Base Damage: " + baseAmount);
                            //System.out.println("Bonus Amount: " + bonusDamage);
                            //System.out.println("Total Damage: " + totalDamage);
                            event.setAmount((float) totalDamage);
                        }
                    } else if (percent <= 80) {
                        //System.out.println("80 or 90");
                        double totalDamage = calculateBonusDamageAmount(baseAmount, bonusDamage, 1);
                        //System.out.println("Base Damage: " + baseAmount);
                        //System.out.println("Bonus Amount: " + bonusDamage);
                        //System.out.println("Total Damage: " + totalDamage);
                        event.setAmount((float) totalDamage);
                    } else {
                        //System.out.println("Over 90");
                        double totalDamage = calculateBonusDamageAmount(baseAmount, bonusDamage, 1);
                        //System.out.println("Base Damage: " + baseAmount);
                        //System.out.println("Bonus Amount: " + bonusDamage);
                        //System.out.println("Total Damage: " + totalDamage);

                        event.setAmount((float) totalDamage);
                    }
                }
            }
        }
    }

    // Calculates bonus damage as well as a modifier to the damage
    /*
        Since Java has a weird way of doing optional parameters, just set factor
        to 1 to not do the division.
    */
    private double calculateBonusDamageAmount(float baseAmount, float bonus, double factor) {
        float totalDamage = (float) (baseAmount + (bonus / factor));
        //System.out.println("Damage: " + totalDamage);

        return Math.floor(totalDamage);
    }

    // Calculates reduced damage about by division
    private double calculateReducedDamageAmount(float baseAmount, double factor) {
        float totalDamage = (float) (baseAmount / factor);
        //System.out.println("Damage: " + totalDamage);

        return Math.floor(totalDamage);
    }
}
