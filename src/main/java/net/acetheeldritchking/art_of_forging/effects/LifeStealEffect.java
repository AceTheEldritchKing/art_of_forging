package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.networking.AoFPackets;
import net.acetheeldritchking.art_of_forging.networking.packet.LifeStealParticlesS2CPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class LifeStealEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(lifeSteal, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                lifeStealName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (lifeStealTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (lifeSteal, 1.0D)), StatsHelper.withFormat
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
                // Flat amount of health gained
                float level = item.getEffectLevel(heldStack, lifeSteal);
                // Chance for effect to proc
                float eff = item.getEffectEfficiency(heldStack, lifeSteal);

                if (level > 0 && eff > (target.getRandom().nextFloat() * 100)) {
                    attacker.heal(level);
                    target.playSound(SoundEvents.WITHER_HURT, 0.2F, 1.0F);

                    // Spawns particles
                    AoFPackets.sendToEntity(new LifeStealParticlesS2CPacket(target.getX(), target.getY(0.5), target.getZ()), target);
                }
            }
        }
    }
}
