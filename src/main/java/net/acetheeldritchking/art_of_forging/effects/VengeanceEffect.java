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

public class VengeanceEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(vengeanceEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, vengeanceName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue
                        (vengeanceTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (vengeanceEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal)));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event)
    {
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Extra damage
                double level = item.getEffectLevel(heldStack, vengeanceEffect);

                // Hearts left
                float eff = item.getEffectEfficiency(heldStack, vengeanceEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                // Bonus damage as a percent
                float bonusDamage = baseAmount * ((float) level/ 100);
                // Attacker health
                float health = attacker.getHealth();

                if (level > 0 && eff >= health)
                {
                    event.setAmount(baseAmount + bonusDamage);
                }
            }
        }
    }
}
