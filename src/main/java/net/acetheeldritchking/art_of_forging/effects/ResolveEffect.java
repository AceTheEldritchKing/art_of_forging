package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class ResolveEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(resolveEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                resolveName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (resolveTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (resolveEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal))
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        ItemStack heldStack = event.player.getMainHandItem();

        if (heldStack.getItem() instanceof ModularItem item) {
            // Duration of potion
            int level = item.getEffectLevel(heldStack, resolveEffect);

            // Hearts left
            float eff = item.getEffectEfficiency(heldStack, resolveEffect);

            // Attacker health
            float health = event.player.getHealth();

            if (level > 0 && eff >= health) {
                applyEffects(level, event.player);
            }
        }
    }

    private void applyEffects(int duration, LivingEntity user) {
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration * 20,
                1, true, true, true));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration * 20,
                1, true, true, true));
        user.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration * 20,
                1, true, true, true));
    }
}
