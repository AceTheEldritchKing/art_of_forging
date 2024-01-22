package net.acetheeldritchking.art_of_forging.item.modular;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import se.mickelus.tetra.gui.GuiModuleOffsets;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.module.ItemModule;
import se.mickelus.tetra.module.ItemUpgradeRegistry;
import se.mickelus.tetra.module.schematic.RemoveSchematic;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModularArtifact extends ModularItem implements ICurio {
    public final static String artifactCasing = "artifact/casing";
    public final static String artifactInternal = "artifact/internal";

    public final static String artifactAttachment = "artifact/attachment";

    public static final String identifier = "modular_artifact";

    private static final GuiModuleOffsets majorOffsets = new GuiModuleOffsets(-13, -1, 3, 19);
    private static final GuiModuleOffsets minorOffsets = new GuiModuleOffsets(6, 1);

    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_artifact"
    )
    public static ModularArtifact instance;

    public ModularArtifact() {
        super(new Item.Properties().stacksTo(1).fireResistant());

        canHone = false;

        majorModuleKeys = new String[]{artifactCasing, artifactInternal};
        minorModuleKeys = new String[]{artifactAttachment};

        requiredModules = new String[]{artifactCasing, artifactInternal};

        RemoveSchematic.registerRemoveSchematics(this, identifier);
    }

    @Override
    public Collection<ItemModule> getAllModules(ItemStack stack) {
        CompoundTag stackTag = stack.getTag();
        if (stackTag != null) {
            return Stream.concat(Arrays.stream(getMajorModuleKeys()), Arrays.stream(getMinorModuleKeys()))
                    .map(stackTag::getString)
                    .map(ItemUpgradeRegistry.instance::getModule)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public GuiModuleOffsets getMajorGuiOffsets() {
        return majorOffsets;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public GuiModuleOffsets getMinorGuiOffsets() {
        return minorOffsets;
    }

    @Override
    public ItemStack getStack() {
        return null;
    }
}
