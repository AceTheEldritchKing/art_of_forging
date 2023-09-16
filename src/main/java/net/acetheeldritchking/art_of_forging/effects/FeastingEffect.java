package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class FeastingEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(feastingEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, feastingName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.decimalLabel, new TooltipGetterMultiValue
                        (feastingTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (feastingEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal)));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event)
    {
        LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Flat amount of hunger gained
                int level = item.getEffectLevel(heldStack, feastingEffect);

                // Chance for effect to proc
                float eff = item.getEffectEfficiency(heldStack, feastingEffect);

                if (level > 0 && eff > (target.getRandom().nextFloat()*100) && attacker instanceof Player player)
                {
                    FoodData playerFood = player.getFoodData();

                    player.getFoodData().setFoodLevel( Math.min(playerFood.getFoodLevel() + level, playerFood.getFoodLevel()));

                    if (target instanceof Player targetPlayer)
                    {
                        FoodData targetFood = targetPlayer.getFoodData();
                        // This should reduce hunger, hopefully
                        targetPlayer.getFoodData().setFoodLevel( Math.max(targetFood.getFoodLevel() - level, 0));
                    }
                }
            }
        }
    }
}
