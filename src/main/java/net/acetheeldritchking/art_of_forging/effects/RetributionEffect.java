package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class RetributionEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(retributionEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                retributionName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (retributionTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (retributionEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal))
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = target.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Damage attacker
                int level = item.getEffectLevel(heldStack, retributionEffect);

                // Chance to proc
                float eff = item.getEffectEfficiency(heldStack, retributionEffect);

                if (level > 0 && eff > (attacker.getRandom().nextFloat() * 100)) {
                    attacker.hurt(attacker.damageSources().generic(), level);
                    target.level().playSound(null, attacker.getY(), attacker.getY(), attacker.getZ(),
                            SoundEvents.THORNS_HIT, SoundSource.PLAYERS, 0.5F, 1.0F);
                }
            }
        }
    }
}
