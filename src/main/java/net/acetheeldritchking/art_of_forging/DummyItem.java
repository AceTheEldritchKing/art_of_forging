package net.acetheeldritchking.art_of_forging;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import se.mickelus.tetra.blocks.scroll.ScrollData;
import se.mickelus.tetra.blocks.scroll.ScrollItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@ParametersAreNonnullByDefault
public class DummyItem extends Item {
    public DummyItem() {
        super((new Properties()).stacksTo(1));
    }

    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (true) {
            items.add(this.setupSchematic("tetra/flamberge_blade", "detail:art_of_forging",new String[]{"sword/flamberge_blade"}, false, 2, 16750098, 6, 15, 4, 7));
            items.add(this.setupSchematic("single/head/halberd_head/halberd_head", "detail:art_of_forging",new String[]{"single/head/halberd_head/halberd_head"}, false, 2, 4475647, 8, 1, 4, 5));
            items.add(this.setupSchematic("sword/key_guard", "detail:art_of_forging",new String[]{"sword/key_guard"}, false, 2, 16442377, 0, 1, 9, 4));
            items.add(this.setupSchematic("single/head/mace_head/mace_head", "detail:art_of_forging",new String[]{"single/head/mace_head/mace_head"}, false, 2, 5636192, 9, 3, 6, 2));
            items.add(this.setupSchematic("tetra/crucible_blade", "detail:art_of_forging",new String[]{"sword/crucible_blade"}, false, 2, 16719360, 8, 7, 9, 2));
            items.add(this.setupSchematic("bow/stave/dreadnought_stave", "detail:art_of_forging",new String[]{"bow/stave/dreadnought_stave"}, false, 2, 15971103, 8, 1, 9, 5));
            items.add(this.setupSchematic("sword/katana/katana_blade", "detail:art_of_forging",new String[]{"sword/katana/katana_blade"}, false, 2, 14417680, 5, 10, 13, 2));
            items.add(this.setupSchematic("bow/string/compound_string", "detail:art_of_forging",new String[]{"bow/string/compound_string"}, false, 1, 1697160, 15, 13, 12, 14));
            items.add(this.setupSchematic("utilize/hammer", "detail:art_of_forging",new String[]{"utilize/hammer"}, false, 1, 16422889, 1, 15, 12, 8));
            items.add(this.setupSchematic("sword/katana/murasama_blade", "detail:art_of_forging",new String[]{"sword/katana/murasama_blade"}, false, 1, 12919587, 6, 7, 13, 15));
            items.add(this.setupSchematic("tetra/rending_scissor_purple", "detail:art_of_forging",new String[]{"sword/rending_scissor_red", "sword/rending_scissor_purple"}, false, 1, 14885250, 1, 15, 2, 13));
        }
    }

    private ItemStack setupSchematic(String key, String details, boolean isIntricate, int material, int tint, Integer... glyphs) {
        return this.setupSchematic(key, details, new String[]{key}, isIntricate, material, tint, glyphs);
    }

    private ItemStack setupSchematic(String key, String details, String[] schematics, boolean isIntricate, int material, int tint, Integer... glyphs) {
        ScrollData data = new ScrollData(key, Optional.ofNullable(details), isIntricate, material, tint, Arrays.asList(glyphs), (List)Arrays.stream(schematics).map((s) -> {
            return new ResourceLocation("tetra", s);
        }).collect(Collectors.toList()), Collections.emptyList());
        ItemStack itemStack = new ItemStack(ScrollItem.instance);
        data.write(itemStack);
        return itemStack;
    }
}
