package net.acetheeldritchking.art_of_forging.capabilities.carnage;

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

public class PlayerCarnageProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerCarnage> PLAYER_CARNAGE =
            CapabilityManager.get(new CapabilityToken<PlayerCarnage>() {
                // Not overriding anything here
            });

    private PlayerCarnage carnage = null;
    private final LazyOptional<PlayerCarnage> optional = LazyOptional.of(this::createPlayerCarnage);

    private PlayerCarnage createPlayerCarnage() {
        if (this.carnage == null)
        {
            this.carnage = new PlayerCarnage();
        }

        return this.carnage;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_CARNAGE)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerCarnage().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCarnage().loadNBTdata(nbt);
    }
}
