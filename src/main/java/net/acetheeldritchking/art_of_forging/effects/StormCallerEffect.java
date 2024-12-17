package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.ModularItem;

import java.util.Objects;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;

public class StormCallerEffect {

    /*@OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter = new StatGetterEffectLevel(stormCaller, 1);
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                stormCallerName, 0, 10, false, false, false,
                statGetter, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue
                        (stormCallerTooltip, StatsHelper.withStats
                                (statGetter, new StatGetterEffectEfficiency
                                        (stormCaller, 1.0D)), StatsHelper.withFormat
                                (StatFormat.noDecimal, StatFormat.noDecimal))
        );

        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }*/

    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity eAttacker = event.getSource().getEntity();

        if (eAttacker instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // How many entities to summon
                int level = item.getEffectLevel(heldStack, stormCaller);
                // Chance for effect to proc
                float eff = item.getEffectEfficiency(heldStack, stormCaller);

                if (level > 0 && !attacker.level().isClientSide()
                        && eff > (target.getRandom().nextFloat() * 100)) {
                    ServerLevel world = (ServerLevel) attacker.level();
                    ServerPlayer player = (ServerPlayer) attacker;
                    BlockPos position = target.blockPosition();

                    // Summoning lightning based on effect level
                    for (int i = 0; i < level; i++) {
                        world.addFreshEntity(Objects.requireNonNull(EntityType.LIGHTNING_BOLT.spawn(world, null, player, position,
                                MobSpawnType.TRIGGERED, true, true)));
                    }
                }
            }
        }
    }
}
