package net.acetheeldritchking.art_of_forging.capabilities.karma;

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

public class PlayerKarmaProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerKarma> PLAYER_KARMA =
            CapabilityManager.get(new CapabilityToken<PlayerKarma>() {
                // Not overriding anything here
            });

    private PlayerKarma karma = null;
    private final LazyOptional<PlayerKarma> optional = LazyOptional.of(this::createPlayerKarma);

    private PlayerKarma createPlayerKarma() {
        if (this.karma == null) {
            this.karma = new PlayerKarma();
        }

        return this.karma;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_KARMA) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerKarma().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerKarma().loadNBTdata(nbt);
    }
}
