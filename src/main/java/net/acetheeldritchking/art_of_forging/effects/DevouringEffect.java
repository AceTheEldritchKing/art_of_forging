package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.capabilities.devouring.PlayerDevouring;
import net.acetheeldritchking.art_of_forging.capabilities.devouring.PlayerDevouringProvider;
import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class DevouringEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(devouringEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                devouringEffectName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(devouringEffectTooltip, statGetter)
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Effect
                int level = item.getEffectLevel(heldStack, devouringEffect);

                if (level > 0 && !attacker.level().isClientSide() && attacker instanceof Player player) {
                    // System.out.println("Reset!");

                    player.getCapability(PlayerDevouringProvider.PLAYER_DEVOURING).ifPresent
                            (PlayerDevouring::resetDevour);

                    // System.out.println("Removing effect..." + PotionEffects.DEVOURING.get());
                    player.removeEffect(PotionEffects.DEVOURING.get());
                }
            }
        }
    }

    // For Devouring
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ItemStack heldStack = event.player.getMainHandItem();

        // Every 5 seconds
        if (event.player.tickCount % 100 == 0 && heldStack.getItem() instanceof ModularItem item) {
            // System.out.println("adding");

            // Potency of effect
            int level = item.getEffectLevel(heldStack, devouringEffect);

            // Duration of effect
            int eff = (int) item.getEffectEfficiency(heldStack, devouringEffect);

            if (level > 0 && !event.player.hasEffect(PotionEffects.DEVOURING.get()) && !event.player.level().isClientSide()) {
                event.player.getCapability(PlayerDevouringProvider.PLAYER_DEVOURING).ifPresent(devouring
                        -> {
                    // System.out.println("current level "+ devouring.getDevour());

                    devouring.addDevour(1);

                    if (devouring.getDevour() >= 30) {
                        // System.out.println("Adding effect..." + PotionEffects.DEVOURING.get());

                        event.player.addEffect(new MobEffectInstance(PotionEffects.DEVOURING.get(),
                                eff * 20, level, true, true, true));
                    }
                });
            } else if (heldStack.getItem() != item && event.player.hasEffect(PotionEffects.DEVOURING.get()) && !event.player.level().isClientSide()) {
                // System.out.println("Reset!");

                event.player.getCapability(PlayerDevouringProvider.PLAYER_DEVOURING).ifPresent
                        (PlayerDevouring::resetDevour);

                // System.out.println("Removing effect..." + PotionEffects.DEVOURING.get());
                event.player.removeEffect(PotionEffects.DEVOURING.get());
            }
        }
    }

}
