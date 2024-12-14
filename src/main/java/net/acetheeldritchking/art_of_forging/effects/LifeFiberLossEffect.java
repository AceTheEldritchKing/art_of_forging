package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class LifeFiberLossEffect {

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(lifeFiberLoss, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                lifeFiberLossName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(lifeFiberLossTooltip, statGetter)
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Level of the potion effect
                int level = item.getEffectLevel(heldStack, lifeFiberLoss);
                // Duration of effect
                int eff = (int) item.getEffectEfficiency(heldStack, lifeFiberLoss);

                // Add effects to player on entity death
                if (level > 0) {
                    grantEffects(attacker, eff, level);
                }
            }
        }
    }

    private void grantEffects(LivingEntity target, int duration, int level) {
        target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, duration * 20,
                level - 1, true, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration * 20,
                level - 1, true, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, duration * 20,
                level - 1, true, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration * 20,
                0, true, true, true));
    }
}
