package net.acetheeldritchking.art_of_forging.effects.curio;

import net.acetheeldritchking.art_of_forging.capabilities.karma.PlayerKarmaProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class CurioKarmaEffect implements ICurioItem {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(karmaInfusedEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, karmaInfusedName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (karmaInfusedTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    // Add or remove karma points
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        Entity entity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (entity instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> inv.findCurios
                    (itemStack -> itemStack.getItem() instanceof ModularItem).forEach(
                    slotResult -> {
                        slotResult.stack();

                        ItemStack itemStack = slotResult.stack();
                        ModularItem item = (ModularItem) itemStack.getItem();

                        // Just have the effect
                        int level = item.getEffectLevel(itemStack, karmaInfusedEffect);

                        if (level > 0 && !player.level().isClientSide()) {
                            if (target instanceof Monster) {
                                player.getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(karma -> {
                                    karma.addKarma(1);
                                });
                            } else {
                                player.getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(karma -> {
                                    karma.subKarma(1);
                                });
                            }
                        }
                    }
            ));
        }
    }

    // Karma buffs/debuffs
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // Finds curio and applies effect
        CuriosApi.getCuriosInventory(player).ifPresent(inv -> inv.findCurios
                (itemStack -> itemStack.getItem() instanceof ModularItem).forEach(
                slotResult -> {
                    slotResult.stack();

                    if (event.player.tickCount % 20 == 0) {
                        ItemStack itemStack = slotResult.stack();
                        ModularItem item = (ModularItem) itemStack.getItem();

                        // Effect duration
                        int level = item.getEffectLevel(itemStack, karmaInfusedEffect);

                        if (level > 0 && !player.level().isClientSide()) {
                            player.getCapability(PlayerKarmaProvider.PLAYER_KARMA).ifPresent(karma -> {
                                // Do stuff here
                                switch (karma.getKarma()) {
                                    case -5 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, level * 20,
                                                4, true, true, true));
                                        break;
                                    }
                                    case -4 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, level * 20,
                                                3, true, true, true));
                                        break;
                                    }
                                    case -3 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, level * 20,
                                                2, true, true, true));
                                        break;
                                    }
                                    case -2 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, level * 20,
                                                1, true, true, true));
                                        break;
                                    }
                                    case -1 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, level * 20,
                                                0, true, true, true));
                                        break;
                                    }
                                    case 0 -> {
                                        player.removeEffect(MobEffects.UNLUCK);
                                        player.removeEffect(MobEffects.LUCK);
                                    }
                                    case 1 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, level * 20,
                                                0, true, true, true));
                                        break;
                                    }
                                    case 2 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, level * 20,
                                                1, true, true, true));
                                        break;
                                    }
                                    case 3 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, level * 20,
                                                2, true, true, true));
                                        break;
                                    }
                                    case 4 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, level * 20,
                                                3, true, true, true));
                                        break;
                                    }
                                    case 5 -> {
                                        player.addEffect(new MobEffectInstance(MobEffects.LUCK, level * 20,
                                                4, true, true, true));
                                        break;
                                    }
                                }
                            });
                        }
                    }
                }
        ));
    }

}
