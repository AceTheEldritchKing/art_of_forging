package net.acetheeldritchking.art_of_forging.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class ScrollHelper {
    private static final Codec<ScrollHelper> codec = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("key").forGetter(i -> i.key),
            Codec.STRING.optionalFieldOf("details").forGetter(i -> Optional.ofNullable(i.details)),
            Codec.BOOL.fieldOf("intricate").forGetter(i -> i.isIntricate),
            Codec.INT.fieldOf("material").forGetter(i -> i.material),
            CustomHexCodec.instance.fieldOf("ribbon").forGetter(i -> i.ribbon),
            Codec.INT.listOf().optionalFieldOf("glyphs", Collections.emptyList()).forGetter(i -> i.glyphs),
            ResourceLocation.CODEC.listOf().optionalFieldOf("schematics", Collections.emptyList()).forGetter(i -> i.schematics),
            ResourceLocation.CODEC.listOf().optionalFieldOf("effects", Collections.emptyList()).forGetter(i -> i.craftingEffects)
    ).apply(instance, ScrollHelper::new));
    public String key;
    public String details;
    public boolean isIntricate;
    public int material = 0;
    public int ribbon = 0xffffff;
    public List<Integer> glyphs = Collections.emptyList();
    public List<ResourceLocation> schematics = Collections.emptyList();
    public List<ResourceLocation> craftingEffects = Collections.emptyList();

    public ScrollHelper() {
        key = "unknown";
    }

    public ScrollHelper(String name, Optional<String> details, boolean isIntricate, int material, int ribbon, List<Integer> glyphs, List<ResourceLocation> schematics, List<ResourceLocation> craftingEffects) {
        this.key = name;
        this.details = details.orElse(null);
        this.isIntricate = isIntricate;

        this.material = material;
        this.ribbon = ribbon;
        this.glyphs = glyphs;

        if (!schematics.isEmpty()) {
            this.schematics = schematics;
        }

        if (!craftingEffects.isEmpty()) {
            this.craftingEffects = craftingEffects;
        }
    }

    public static CompoundTag write(ScrollHelper[] data, CompoundTag tag) {
        ListTag list = Arrays.stream(data)
                .map(scroll -> ScrollHelper.codec.encodeStart(NbtOps.INSTANCE, scroll))
                .map(DataResult::result)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ListTag::new));
        tag.put("data", list);
        return tag;
    }

    public void write(ItemStack itemStack) {
        itemStack.addTagElement("BlockEntityTag", ScrollHelper.write(new ScrollHelper[]{this}, new CompoundTag()));
    }
}
