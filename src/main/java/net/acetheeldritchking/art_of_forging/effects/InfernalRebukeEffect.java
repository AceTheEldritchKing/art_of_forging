package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class InfernalRebukeEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(infernalRebukeEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                infernalRebukeName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (infernalRebukeTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (infernalRebukeEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal))
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = target.getMainHandItem();
            //ItemStack offHeldStack = target.getOffhandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Seconds on fire
                int level = item.getEffectLevel(heldStack, infernalRebukeEffect);

                // Chance to proc
                float eff = item.getEffectEfficiency(heldStack, infernalRebukeEffect);

                // Getting target coords
                double xTarget = (int) target.getX();
                double zTarget = (int) target.getZ();
                // Getting attacker coords
                int xAttacker = (int) attacker.getX();
                int zAttacker = (int) attacker.getZ();

                // Gotta normalize the knockback
                Vec3 vec3 = new Vec3(xTarget, 0, zTarget).subtract(xAttacker, 0, zAttacker).normalize();

                if (level > 0 && eff > (attacker.getRandom().nextFloat() * 100)) {
                    attacker.knockback(0.4, vec3.x, vec3.z);
                    attacker.setSecondsOnFire(level);

                    // Play Sound
                    target.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 0.5F, 1.0F);
                }
            }
        }
    }
}
