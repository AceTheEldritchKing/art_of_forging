package net.acetheeldritchking.art_of_forging.capabilities.devouring;

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

public class PlayerDevouringProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerDevouring> PLAYER_DEVOURING =
            CapabilityManager.get(new CapabilityToken<PlayerDevouring>() {
                // Not overriding anything here
            });

    private PlayerDevouring devouring = null;
    private final LazyOptional<PlayerDevouring> optional = LazyOptional.of(this::createPlayerDevouring);

    private PlayerDevouring createPlayerDevouring() {
        if (this.devouring == null) {
            this.devouring = new PlayerDevouring();
        }

        return this.devouring;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_DEVOURING) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerDevouring().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerDevouring().loadNBTdata(nbt);
    }
}
