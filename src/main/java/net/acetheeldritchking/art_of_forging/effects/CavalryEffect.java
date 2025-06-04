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
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class CavalryEffect {

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(cavalryEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                cavalryEffectName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(cavalryEffectTooltip, statGetter)
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Amount of extra damage while riding entity
                int level = item.getEffectLevel(heldStack, cavalryEffect);

                float baseAmount = event.getAmount();
                // Gets bonus damage as percentage
                float bonusDamage = getDecimalPercentage(level, baseAmount);

                // Add bonus damage while riding entity
                if (level > 0 && attacker.isPassenger()) {
                    event.setAmount(getExactPercentage(baseAmount, bonusDamage));
                }
            }
        }
    }
}
