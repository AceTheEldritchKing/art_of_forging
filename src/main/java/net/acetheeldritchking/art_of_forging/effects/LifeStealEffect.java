package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.networking.AoFPackets;
import net.acetheeldritchking.art_of_forging.networking.packet.LifeStealParticlesS2CPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

public class LifeStealEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(lifeSteal, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, lifeStealName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.decimalLabel, new TooltipGetterMultiValue
                        (lifeStealTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (lifeSteal, 1.0D)), StatsHelper.withFormat
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
                // Flat amount of health gained
                float level = item.getEffectLevel(heldStack, lifeSteal);
                // Chance for effect to proc
                float eff = item.getEffectEfficiency(heldStack, lifeSteal);

                if (level > 0 && eff > (target.getRandom().nextFloat()*100))
                {
                    attacker.heal(level);
                    target.playSound(SoundEvents.WITHER_HURT, 0.2F, 1.0F);

                    // Spawns particles
                    AoFPackets.sendToEntity(new LifeStealParticlesS2CPacket(target.getX(), target.getY(0.5), target.getZ()), target);
                }
            }
        }
    }
}
