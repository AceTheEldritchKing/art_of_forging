package net.acetheeldritchking.art_of_forging.effects.curio;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class CurioMagicResistanceEffect implements ICurioItem {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(arcaneProtectionEffect, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                arcaneProtectionName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(arcaneProtectionTooltip, statGetter)
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event) {
        Entity entity = event.getSource().getEntity();

        if (entity instanceof Player player) {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> inv.findCurios
                    (itemStack -> itemStack.getItem() instanceof ModularItem).forEach(
                    slotResult -> {
                        slotResult.stack();

                        ItemStack itemStack = slotResult.stack();
                        ModularItem item = (ModularItem) itemStack.getItem();

                        // Percentage to reduce damage
                        int level = item.getEffectLevel(itemStack, arcaneProtectionEffect);

                        float baseAmount = event.getAmount();
                        // Gets bonus damage as percentage
                        float reductionPercentage = baseAmount * ((float) level / 100);

                        if (level > 0 && event.getSource().is(DamageTypes.MAGIC)) {
                            // System.out.println("Is magic?");
                            // System.out.println("Base amount: " + baseAmount);
                            // System.out.println("Reduction amount: " + reductionPercentage);
                            // System.out.println("Total amount: " + (baseAmount - reductionPercentage));

                            event.setAmount(baseAmount - reductionPercentage);
                        }
                    }
            ));
        }
    }
}
