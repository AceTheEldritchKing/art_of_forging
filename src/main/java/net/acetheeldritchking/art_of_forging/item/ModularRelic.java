package net.acetheeldritchking.art_of_forging.item;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.mickelus.tetra.gui.GuiModuleOffsets;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.module.ItemModule;
import se.mickelus.tetra.module.ItemUpgradeRegistry;
import net.acetheeldritchking.art_of_forging.ArtOfForging;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModularRelic extends ModularItem implements ICurio{
    public final static String relicCasing = "relic/casing";
    public final static String relicInternal = "relic/internal";
    public final static String relicAttachment = "relic/attachment";
    private static final Logger logger = LogManager.getLogger();
    private static final GuiModuleOffsets majorOffsets = new GuiModuleOffsets(-13, -1, 3, 19);
    private static final GuiModuleOffsets minorOffsets = new GuiModuleOffsets(6, 1);
    public static final String identifier = "modular_relic";

    public ModularRelic() {
        super(new Properties());
        majorModuleKeys = new String[]{relicCasing, relicInternal};
        minorModuleKeys = new String[]{relicAttachment};
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

    @ObjectHolder(ArtOfForging.MOD_ID + ":" + identifier)
    public static se.mickelus.tetra.items.modular.impl.ModularDoubleHeadedItem instance;

    @Override
    public String[] getMajorModuleKeys() {
        return this.majorModuleKeys;
    }

    @Override
    public String[] getMinorModuleKeys() {
        return this.minorModuleKeys;
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
    public String getDisplayNamePrefixes(ItemStack itemStack) {
        return "Modular Relic";
    }

    @Override
    public ItemStack getStack() {
        return null;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        items.add(setupRelic("iron", "emerald", "iron"));
    }

    private ItemStack setupRelic(String casing, String internal, String attachment) {
        ItemStack itemStack = new ItemStack(this);
        IModularItem.putModuleInSlot(itemStack, relicCasing, "relic/casing", "relic_casing/", "relic_casing/" + casing);
        IModularItem.putModuleInSlot(itemStack, relicInternal, "relic/internal", "relic_internal/", "relic_internal/" + internal);
        IModularItem.putModuleInSlot(itemStack, relicAttachment, "relic/attachment", "relic_attachment/", "relic_attachment/" + attachment);
        IModularItem.updateIdentifier(itemStack);

        return itemStack;
    }


}
