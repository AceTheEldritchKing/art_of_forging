package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
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

public class KnockbackEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(knockbackEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, knockbackEffectName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (knockbackEffectTooltip, effectStatGetter));
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
                if (level > 0)
                {
                    target.knockback(level,
                            eff == 1 ? vec3r.x : vec3.x,
                            eff == 1 ? vec3r.z : vec3.z);
                }
            }
        }
    }
}
