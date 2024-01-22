package net.acetheeldritchking.art_of_forging.effects.curio;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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

public class CurioMagicResistanceEffect implements ICurioItem {
    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(arcaneProtectionEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, arcaneProtectionName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterInteger
                        (arcaneProtectionTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingHurtEvent(LivingHurtEvent event)
    {
        Entity entity = event.getSource().getEntity();

        if (entity instanceof Player player)
        {
            CuriosApi.getCuriosHelper().findCurios(player,itemStack ->
                    itemStack.getItem() instanceof ModularItem).forEach
                    (slotResult -> {
                        slotResult.stack();

                        ItemStack itemStack = slotResult.stack();
                        ModularItem item = (ModularItem) itemStack.getItem();

                        // Percentage to reduce damage
                        int level = item.getEffectLevel(itemStack, arcaneProtectionEffect);

                        float baseAmount = event.getAmount();
                        // Gets bonus damage as percentage
                        float reductionPercentage = baseAmount * ((float) level / 100);

                        if (level > 0 && event.getSource().isMagic())
                        {
                            // System.out.println("Is magic?");
                            // System.out.println("Base amount: " + baseAmount);
                            // System.out.println("Reduction amount: " + reductionPercentage);
                            // System.out.println("Total amount: " + (baseAmount - reductionPercentage));

                            event.setAmount(baseAmount - reductionPercentage);
                        }
                    });
        }
    }
    /*
    ItemStack itemStack = slotResult.stack();
                    ModularItem item = (ModularItem) itemStack.getItem();

                    // Percentage to reduce damage
                    int level = item.getEffectLevel(itemStack, arcaneProtectionEffect);

                    float baseAmount = event.getAmount();
                    // Gets bonus damage as percentage
                    float reductionPercentage = baseAmount * ((float) level / 100);

                    if (level > 0 && event.getSource().isMagic())
                    {
                        // System.out.println("Is magic?");
                        // System.out.println("Base amount: " + baseAmount);
                        // System.out.println("Reduction amount: " + reductionPercentage);
                        // System.out.println("Total amount: " + (baseAmount - reductionPercentage));

                        event.setAmount(baseAmount - reductionPercentage);
                    }
     */
}
