package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class EsotericEdgeEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(esotericEdgeEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                esotericEdgeName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(esotericEdgeTooltip, statGetter)
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

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
