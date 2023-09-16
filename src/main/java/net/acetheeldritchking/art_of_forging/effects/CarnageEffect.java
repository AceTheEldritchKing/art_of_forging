package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.capabilities.carnage.PlayerCarnageProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class CarnageEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(carnageEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, carnageName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (carnageTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event)
    {
        //LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Just to have effect initialized
                int level = item.getEffectLevel(heldStack, carnageEffect);

                // Used for bonus damage
                float eff = item.getEffectEfficiency(heldStack, carnageEffect);

                // Base attack damage
                float baseAmount = event.getAmount();
                /*
                *  Bonus damage as a percent.
                *  Damage specified in the JSON file will be the starting
                *  bonus. At each carnage point, the starting amount will
                *  be added to by 10.
                *
                *  I really struggled to write the above comment please bare with me.
                */
                float bonusDamage = baseAmount * (eff/100);

                if (level > 0 && attacker instanceof Player player)
                {
                    player.getCapability(PlayerCarnageProvider.PLAYER_CARNAGE).ifPresent(carnage -> {
                        // System.out.println("Current level of carnage: " + carnage.getCarnage());
                        carnage.addCarnage(1);

                        // Bonus damage based on carnage stack
                        switch (carnage.getCarnage()) {
                            case 1 -> {
                                // System.out.println("Case 1");

                                event.setAmount(baseAmount);
                                break;
                            }
                            case 2 -> {
                                // System.out.println("Case 2");

                                event.setAmount((baseAmount + bonusDamage));
                                break;
                            }
                            case 3 -> {
                                // System.out.println("Case 3");

                                event.setAmount((baseAmount + bonusDamage) + addedPercent(baseAmount, 0.10F));
                                break;
                            }
                            case 4 -> {
                                // System.out.println("Case 4");

                                event.setAmount((baseAmount + bonusDamage) + addedPercent(baseAmount, 0.20F));
                                break;
                            }
                            case 5 -> {
                                // System.out.println("Case 5");

                                event.setAmount((baseAmount + bonusDamage) + addedPercent(baseAmount, 0.30F));

                                // Play Sounds
                                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.RAVAGER_ROAR, SoundSource.PLAYERS, 0.8F, 1.0F);
                                break;
                            }
                        }
                    });
                }
            }
        }
    }

    private float addedPercent(float base, float added)
    {
        float total = base * added;

        return total;
    }
}
