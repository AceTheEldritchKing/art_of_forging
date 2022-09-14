package net.acetheeldritchking.art_of_forging.mixins;


import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import se.mickelus.tetra.blocks.scroll.ScrollItem;
import se.mickelus.tetra.items.TetraItemGroup;

@Mixin(value = ScrollItem.class)
public class ScrollItemMixin{
    @Inject(
            at = @At("HEAD"),
            method = "Lse/mickelus/tetra/blocks/scroll/ScrollItem;fillItemCategory(Lnet/minecraft/world/item/CreativeModeTab;Lnet/minecraft/core/NonNullList;)V",
            remap = false,
            cancellable = true
    )
    private void registerScrolls(CreativeModeTab group, NonNullList<ItemStack> items, CallbackInfo ci){
        if(group== TetraItemGroup.instance){
            //tetra:single/head/halberd_head/halberd_head
            items.add(this.setupSchematic("tetra/flamberge_blade", "detail:art_of_forging",new String[]{"sword/flamberge_blade"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("sword/katana/katana_blade", "detail:art_of_forging",new String[]{"sword/katana/katana_blade"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("sword/katana/murasama_blade", "detail:art_of_forging",new String[]{"sword/katana/murasama_blade"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("single/head/halberd_head/halberd_head", "detail:art_of_forging",new String[]{"single/head/halberd_head/halberd_head"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("single/head/mace_head/mace_head", "detail:art_of_forging",new String[]{"single/head/mace_head/mace_head"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("tetra/crucible_blade", "detail:art_of_forging",new String[]{"sword/crucible_blade"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("bow/stave/dreadnought_stave", "detail:art_of_forging",new String[]{"bow/stave/dreadnought_stave"}, false, 2, 8739251, 6, 7, 11, 7));
            items.add(this.setupSchematic("bow/string/compound_string", "detail:art_of_forging",new String[]{"bow/string/compound_string"}, false, 2, 8739251, 6, 7, 11, 7));
        }
        ci.cancel();
    }
    @Shadow
    private ItemStack setupSchematic(String key, String details, boolean isIntricate, int material, int tint, Integer... glyphs) {return null;};
    @Shadow
    private ItemStack setupSchematic(String key, String details, String[] schematics, boolean isIntricate, int material, int tint, Integer... glyphs) {return null;};
}
