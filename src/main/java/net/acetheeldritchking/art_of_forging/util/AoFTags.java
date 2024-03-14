package net.acetheeldritchking.art_of_forging.util;

import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class AoFTags {
    // Don't ever ask me to do entity tags again /hj
    public static class Entities {
        public static final TagKey<EntityType<?>> BOSS_ENTITIES = TagKey.create
                (ForgeRegistries.ENTITY_TYPES.getRegistryKey(),
                        new ResourceLocation(ArtOfForging.MOD_ID, "bosses"));
    }
}
