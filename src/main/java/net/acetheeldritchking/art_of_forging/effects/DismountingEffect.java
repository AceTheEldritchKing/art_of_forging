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

public class DismountingEffect {

    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(dismountingEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, dismountingEffectName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue
                        (dismountingEffectTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (dismountingEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.oneDecimal, StatFormat.oneDecimal)));
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
                // Amount of extra damage to mounted entity
                int level = item.getEffectLevel(heldStack, dismountingEffect);

                float baseAmount = event.getAmount();
                // Gets bonus damage as percentage
                float bonusDamage = getDecimalPercentage(level, baseAmount);

                // Chance to dismount entity
                double eff = item.getEffectEfficiency(heldStack, dismountingEffect);

                // Add bonus damage to the mounted entity
                // Also has chance to dismount entities
                if (level > 0)
                {
                    // If target is riding
                    if (target.isPassenger())
                    {
                        event.setAmount(getExactPercentage(baseAmount, bonusDamage));

                        if (eff > (target.getRandom().nextFloat()*100))
                        {
                            target.stopRiding();
                        }
                    }

                    // If target has passenger
                    if (target.isVehicle())
                    {
                        if (eff > (target.getRandom().nextFloat()*100))
                        {
                            target.ejectPassengers();
                        }
                    }
                }
            }
        }
    }
}
