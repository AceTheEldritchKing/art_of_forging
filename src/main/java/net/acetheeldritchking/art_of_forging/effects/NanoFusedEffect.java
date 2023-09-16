package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.AoFRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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

public class NanoFusedEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(nanoFusedEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, nanoFusedName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterInteger
                        (nanoFusedTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    // I looked at Tetra Pak's code to help with this
    @SubscribeEvent
    public void onLivingDropEvent(LivingDropsEvent event)
    {
        LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();
        // System.out.println("Called?");

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Chance to have changed loot drops
                float level = item.getEffectLevel(heldStack, nanoFusedEffect);

                // This is for the randomness to work
                float chance = level/100;

                // System.out.println("Level: " + level);
                if (level > 0)
                {
                    // For the loot drop
                    ItemStack nanoDrop = ItemStack.EMPTY;

                    if (target instanceof Creeper) nanoDrop = new ItemStack(AoFRegistry.POTENT_MIXTURE.get());
                    else if (target instanceof EnderMan) nanoDrop = new ItemStack(AoFRegistry.HEART_OF_ENDER.get());
                    else if (target instanceof Warden) nanoDrop = new ItemStack(AoFRegistry.EERIE_SHARD.get());

                    // Actual drop stuff
                    if (!nanoDrop.isEmpty())
                    {
                        boolean drop = target.level.random.nextFloat() < chance;

                        if (drop)
                        {
                            ItemEntity itemDrop = new ItemEntity
                                    (target.level, target.getX(), target.getY(), target.getZ(), nanoDrop);
                            itemDrop.setDefaultPickUpDelay();
                            event.getDrops().add(itemDrop);
                        }
                    }
                }
            }
        }
    }
}
