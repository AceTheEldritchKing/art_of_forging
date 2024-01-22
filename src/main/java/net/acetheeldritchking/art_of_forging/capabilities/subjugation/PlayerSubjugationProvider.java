package net.acetheeldritchking.art_of_forging.capabilities.subjugation;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerSubjugationProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSubjugation> PLAYER_SUBJUGATION =
            CapabilityManager.get(new CapabilityToken<PlayerSubjugation>() {
                // Not overriding anything here
            });

    private PlayerSubjugation subjugation = null;
    private final LazyOptional<PlayerSubjugation> optional = LazyOptional.of(this::createPlayerSubjugation);

    private PlayerSubjugation createPlayerSubjugation() {
        if (this.subjugation == null)
        {
            this.subjugation = new PlayerSubjugation();
        }

        return this.subjugation;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SUBJUGATION)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSubjugation().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSubjugation().loadNBTdata(nbt);
    }
}
