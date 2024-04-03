package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class GoliathSlayerEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(goliathSlayerEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, goliathSlayerName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterPercentageDecimal
                        (goliathSlayerTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event)
    {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Percent to increase damage
                double level = item.getEffectLevel(heldStack, goliathSlayerEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                // Bonus damage as a percent
                float bonusDamage = getDecimalPercentage((float) level, baseAmount);
                // Base target health, constant
                final float TARGET_MAX_HEALTH = target.getMaxHealth();

                // Either a boss mob or has health greater than 80 hearts
                if (level > 0 && isBossEntity(target.getType()) || TARGET_MAX_HEALTH >= 80)
                {
                    System.out.println("Base: " + baseAmount);
                    System.out.println("Bonus: " + bonusDamage);
                    System.out.println("Total: " + getExactPercentage(baseAmount, bonusDamage));
                    event.setAmount(getExactPercentage(baseAmount, bonusDamage));
                }
            }
        }
    }
}
