package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.capabilities.conquer.PlayerConquerProvider;
import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
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

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class ConqueringEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(conqueringEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, conqueringName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (conqueringTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    // Add conquer on death
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Just for init effect
                int level = item.getEffectLevel(heldStack, conqueringEffect);

                if (level > 0 && !attacker.level.isClientSide() && attacker instanceof Player player)
                {
                    player.getCapability(PlayerConquerProvider.PLAYER_CONQUER).ifPresent(conquer ->
                    {
                        // System.out.println("Added conquer, current level is: " + conquer.getConquer());
                        conquer.addConquer(1);
                    });
                }
            }
        }
    }

    // Apply debuffs to entities when full conquer
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
                // Effect initialized
                int level = item.getEffectLevel(heldStack, conqueringEffect);

                // Duration of effect
                int eff = (int) item.getEffectEfficiency(heldStack, conqueringEffect);

                if (level > 0)
                {
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, eff*20, 0,
                            true, true, true));

                    // Increase weakness when full conquer
                    if (!attacker.level.isClientSide() && attacker instanceof Player player)
                    {
                        player.getCapability(PlayerConquerProvider.PLAYER_CONQUER).ifPresent(conquer ->
                        {
                            if (conquer.getConquer() == 5)
                            {
                                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, eff*20, 1,
                                        true, true, true));
                            }
                        });
                    }
                }
            }
        }
    }

    // Apply strength & severing to player when full conquer
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        ItemStack heldStack = player.getMainHandItem();

        // Every second
        if (player.tickCount % 20 == 0 && heldStack.getItem() instanceof ModularItem item)
        {
            // Potency of severing
            int level = item.getEffectLevel(heldStack, conqueringEffect);

            // Duration of strength
            int eff = (int) item.getEffectEfficiency(heldStack, conqueringEffect);

            if (level > 0 && !player.level.isClientSide())
            {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, eff*20, 0,
                        true, true, true));

                player.getCapability(PlayerConquerProvider.PLAYER_CONQUER).ifPresent(conquer ->
                {
                    if (conquer.getConquer() == 5)
                    {
                        player.addEffect(new MobEffectInstance(PotionEffects.MORTAL_WOUNDS.get(), 20, level,
                                false, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, eff*20, 1,
                                true, true, true));
                    }
                });
            }
        }
    }
}
