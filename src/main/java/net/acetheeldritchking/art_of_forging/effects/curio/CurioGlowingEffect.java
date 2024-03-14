package net.acetheeldritchking.art_of_forging.effects.curio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class CurioGlowingEffect implements ICurioItem {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(thirdSightEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, thirdSightName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterMultiValue
                        (thirdSightTooltip, StatsHelper.withStats
                                (effectStatGetter, new StatGetterEffectEfficiency
                                        (thirdSightEffect, 1.0D)), StatsHelper.withFormat
                                (StatFormat.oneDecimal, StatFormat.oneDecimal)));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        Entity entity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (entity instanceof Player player) {
            CuriosApi.getCuriosHelper().findCurios(player, itemStack ->
                    itemStack.getItem() instanceof ModularItem).forEach
                    (slotResult -> {
                        slotResult.stack();

                        ItemStack itemStack = slotResult.stack();
                        ModularItem item = (ModularItem) itemStack.getItem();

                        // Duration of glowing
                        int level = item.getEffectLevel(itemStack, thirdSightEffect);

                        if (level > 0) {
                            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, level * 20,
                                    0, false, false, false));
                        }

                    });
        }
    }
}
