package net.acetheeldritchking.art_of_forging.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.stream.Collectors;


@ParametersAreNonnullByDefault
public class DummyItem extends Item {
    protected Minecraft minecraft;
    public DummyItem() {
        super (new Properties ());
        //super(new Properties().tab(se.mickelus.tetra.TetraItemGroup.instance).stacksTo(1));
    }

    private CreativeModeTab tetraTab;
    private Item tetraScroll;

    private CreativeModeTab getTetraTab(){
        if(tetraTab==null){
            CreativeModeTab[] groups = CreativeModeTab.TABS;
            for(int i = 0;i<groups.length;i++){
                if(groups[i].getClass().getCanonicalName() != null && groups[i].getClass().getCanonicalName().contains("tetra")){
                    tetraTab = groups[i];
                    return groups[i];
                }
            }
            for(int i = 0;i<groups.length;i++){
                if(groups[i].getClass().getCanonicalName() != null && groups[i].getClass().getCanonicalName().contains("mickelus")) {
                    tetraTab = groups[i];
                    return groups[i];
                }
            }
            return null;
        }
        else{
            return tetraTab;
        }
    }

    private Item getScrollItem(){
        if(tetraScroll==null)
            tetraScroll = Registry.ITEM.get(new ResourceLocation("tetra:scroll_rolled"));
        return tetraScroll;
    }

    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (group.equals(getTetraTab())) {
            items.add(this.setupSchematic("tetra/flamberge_blade", "art_of_forging",new String[]{"sword/flamberge_blade"}, false, 2, 16750098, 6, 15, 4, 7));
            items.add(this.setupSchematic("single/head/halberd_head/halberd_head", "art_of_forging",new String[]{"single/head/halberd_head/halberd_head"}, false, 1, 4475647, 8, 1, 4, 5));
            items.add(this.setupSchematic("sword/key_guard", "art_of_forging",new String[]{"sword/key_guard"}, false, 1, 16442377, 0, 1, 9, 4));
            items.add(this.setupSchematic("single/head/mace_head/mace_head", "art_of_forging",new String[]{"single/head/mace_head/mace_head"}, false, 1, 5636192, 9, 3, 6, 2));
            items.add(this.setupSchematic("tetra/crucible_blade", "art_of_forging",new String[]{"sword/crucible_blade"}, false, 2, 16719360, 8, 7, 9, 2));
            items.add(this.setupSchematic("bow/stave/dreadnought_stave", "art_of_forging",new String[]{"bow/stave/dreadnought_stave", "bow/stave/dreadnought_cross_stave"}, false, 1, 15971103, 8, 1, 9, 5));
            items.add(this.setupSchematic("sword/katana/katana_blade", "art_of_forging",new String[]{"sword/katana/katana_blade", "sword/tsuba_guard"}, false, 2, 14417680, 5, 10, 13, 2));
            items.add(this.setupSchematic("bow/string/compound_string", "art_of_forging",new String[]{"bow/string/compound_string", "crossbow/string/compound_cross_string"}, false, 1, 1697160, 15, 13, 12, 14));
            items.add(this.setupSchematic("utilize/hammer", "art_of_forging",new String[]{"utilize/hammer"}, false, 2, 16422889, 1, 15, 12, 8));
            items.add(this.setupSchematic("sword/katana/murasama_blade", "otherworldly",new String[]{"sword/katana/murasama_blade"}, false, 2, 12919587, 6, 7, 13, 15));
            items.add(this.setupSchematic("tetra/rending_scissor_complete", "otherworldly",new String[]{"sword/scissor_blade_left", "sword/scissor_blade_right", "sword/rending_scissor_complete"}, false, 2, 14885250, 1, 15, 2, 13));
            items.add(this.setupSchematic("sword/crucible/architects_crucible_blade", "true_crucible",new String[]{"sword/crucible_blade", "sword/crucible/architects_crucible_blade"}, true, 2, 16711746, 8, 7, 9, 2));
            items.add(this.setupSchematic("sword/tonal_blade", "art_of_forging",new String[]{"sword/tonal_blade"}, false, 1, 14350246, 3, 5, 6, 9));
            items.add(this.setupSchematic("sword/thousand_cold_nights", "otherworldly",new String[]{"sword/katana/murasama_blade", "sword/thousand_cold_nights"}, true, 2, 6061184, 7, 8, 14, 13));
        }
    }

    private ItemStack setupSchematic(String key, String details, String[] schematics, boolean isIntricate, int material, int tint, Integer... glyphs) {
        ScrollHelper data = new ScrollHelper(key, Optional.ofNullable(details), isIntricate, material, tint, Arrays.asList(glyphs), (List)Arrays.stream(schematics).map((s) -> {
            return new ResourceLocation("tetra", s);
        }).collect(Collectors.toList()), Collections.emptyList());
        ItemStack itemStack = new ItemStack(getScrollItem());
        data.write(itemStack);
        return itemStack;
    }
}
