package net.acetheeldritchking.art_of_forging.bettercombatcompat.mixins;

import net.acetheeldritchking.art_of_forging.Config;
import net.bettercombat.api.AttributesContainer;
import net.bettercombat.api.WeaponAttributes;
import net.bettercombat.logic.WeaponRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;


@Mixin(value = WeaponRegistry.class, remap = false)
public abstract class WeaponRegistryMixin {
    @Shadow @Final private static Logger LOGGER;

    /**
     * @Smartin
     *
     */
    @Inject(
            at = @At(value = "HEAD"),
            method = "loadAttributes",
            cancellable = true,
            remap = false
    )
    private static void loadAttributes(ResourceManager resourceManager, CallbackInfo ci){
        ci.cancel();
        WeaponRegistryInterface.loadContainers(resourceManager);
        Map<ResourceLocation, AttributesContainer> containers = WeaponRegistryInterface.containers();
        containers.forEach((itemId, container) -> {
            WeaponRegistryInterface.resolveAndRegisterAttributes(itemId, container);
        });
    }

    @Inject(
            at = @At(value = "HEAD"),
            method = "Lnet/bettercombat/logic/WeaponRegistry;getAttributes(Lnet/minecraft/world/item/ItemStack;)Lnet/bettercombat/api/WeaponAttributes;",
            cancellable = true,
            remap = false
    )
    private static void getAttributes(ItemStack itemStack, CallbackInfoReturnable<WeaponAttributes> cir){
        String weaponParent = Config.findWeaponByNBT(itemStack);
        if(weaponParent!=null&&weaponParent!=""){
            WeaponAttributes attributes = WeaponRegistry.getAttributes(new ResourceLocation(weaponParent));
            if(attributes!=null){
                cir.setReturnValue(attributes);
                return;
            }
            else{
                //System.out.println("No WeaponAttributes Found for "+weaponParent);
            }
        }
    }
}
