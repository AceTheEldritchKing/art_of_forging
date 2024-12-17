package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class KnockbackEffect {
    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(knockbackEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                knockbackEffectName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(knockbackEffectTooltip, statGetter)
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
                // Strength of knockback
                float level = item.getEffectLevel(heldStack, knockbackEffect);
                // Reverse or not; makeshift Boolean!
                int eff = (int) item.getEffectEfficiency(heldStack, knockbackEffect);

                // Getting target coords
                int xTarget = (int) target.getX();
                int zTarget = (int) target.getZ();
                // Getting attacker coords
                int xAttacker = (int) attacker.getX();
                int zAttacker = (int) attacker.getZ();

                // Normalize homosexuality
                Vec3 vec3 = new Vec3(xAttacker, 0, zAttacker).subtract(xTarget, 0, zTarget).normalize();
                Vec3 vec3r = new Vec3(xTarget, 0, zTarget).subtract(xAttacker, 0, zAttacker).normalize();

                // Does the knockback
                // Reverses the knockback effect if the eff val equals 1
                if (level > 0) {
                    target.knockback(level,
                            eff == 1 ? vec3r.x : vec3.x,
                            eff == 1 ? vec3r.z : vec3.z);
                }
            }
        }
    }
}
