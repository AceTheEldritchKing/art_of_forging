package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class DisorientingEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(disorientingEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                disorientingName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (disorientingTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (disorientingEffect, 1.0D)), StatsHelper.withFormat
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
                // Level of effect
                int level = item.getEffectLevel(heldStack, disorientingEffect);
                // Duration of effect
                int eff = (int) item.getEffectEfficiency(heldStack, disorientingEffect);

                // Apply potion effects
                if (level > 0 && !MobType.UNDEAD.equals(target.getMobType())) {
                    if (target instanceof Player) {
                        applyEffects(level, eff, target);
                        target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, eff * 20,
                                level, true, true, true));
                    } else if (target instanceof Creeper creeper) {
                        applyEffects(level, eff, creeper);
                        // Stop creeper from exploding
                        // 5X the duration of effect to actually stop the creeper from exploding
                        creeper.addEffect(new MobEffectInstance(PotionEffects.DEFUSE_CREEPER.get(), eff * 100,
                                2, false, false, false));
                    } else {
                        applyEffects(level, eff, target);
                    }
                }
            }
        }
    }

    private void applyEffects(int level, int duration, LivingEntity target) {
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration * 20,
                level, true, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration * 20,
                level, true, true, true));
    }
}
