package net.acetheeldritchking.art_of_forging.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

// I looked at how Farmer's Delight did loot tables because I am tired
public class AddItemModifier extends LootModifier {
    public static final Supplier<Codec<AddItemModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).and(ResourceLocation.CODEC
            .fieldOf("loot_table").forGetter(m -> m.loot_table)).apply(inst, AddItemModifier::new)));
    private final ResourceLocation loot_table;

    public AddItemModifier(LootItemCondition[] conditionsIn, ResourceLocation item) {
        super(conditionsIn);
        this.loot_table = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable addedLootTable = context.getResolver().getLootTable(this.loot_table);
        addedLootTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));

        //generatedLoot.add(new ItemStack(this.item));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}