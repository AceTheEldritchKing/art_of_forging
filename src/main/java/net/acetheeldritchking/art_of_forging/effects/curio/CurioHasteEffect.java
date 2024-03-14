package net.acetheeldritchking.art_of_forging.effects.curio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class CurioHasteEffect implements ICurioItem {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(hasteInfusedEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, hasteInfusedName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (hasteInfusedTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        // Finds curio and applies effect
        CuriosApi.getCuriosHelper().findCurios(player, itemStack ->
                itemStack.getItem() instanceof ModularItem).forEach
                (slotResult -> {
                    slotResult.stack();

                    if (event.player.tickCount % 20 == 0) {
                        ItemStack itemStack = slotResult.stack();
                        ModularItem item = (ModularItem) itemStack.getItem();

                        // Effect potency
                        int level = item.getEffectLevel(itemStack, hasteInfusedEffect);

                        // Effect duration
                        int eff = (int) item.getEffectEfficiency(itemStack, hasteInfusedEffect);

                        if (level > 0) {
                            event.player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, eff * 20,
                                    level - 1, true, true, true));
                        }
                    }
                });
    }
}
