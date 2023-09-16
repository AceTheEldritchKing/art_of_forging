package net.acetheeldritchking.art_of_forging.mixins.tetra;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.items.modular.ItemModularHandheld;

import javax.annotation.Nullable;
import java.util.Arrays;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.aofAbilities;

@Mixin(ItemModularHandheld.class)
public class ItemModularHandheldMixin {
    @Inject(
            method = "handleChargedAbility",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void aof$modifyHandheldChargedAbility(Player player, InteractionHand hand, @Nullable LivingEntity target, @Nullable BlockPos targetPos, @Nullable Vec3 hitVec, int ticksUsed, CallbackInfo cir)
    {
        ItemStack activeStack = player.getItemInHand(hand);
        if (!activeStack.isEmpty() && activeStack.getItem() instanceof ItemModularHandheld) {
            ItemModularHandheld item = (ItemModularHandheld)activeStack.getItem();
            Arrays.stream(aofAbilities).filter((ability) -> ability.canPerform(player, item, activeStack, target, targetPos, ticksUsed)).findFirst().ifPresent((ability) -> {
                ability.perform(player, hand, item, activeStack, target, targetPos, hitVec, ticksUsed);
            });
            player.stopUsingItem();

            cir.cancel();
        }
    }
}
