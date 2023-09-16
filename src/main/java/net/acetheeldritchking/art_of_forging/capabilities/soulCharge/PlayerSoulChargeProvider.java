package net.acetheeldritchking.art_of_forging.capabilities.soulCharge;

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

public class PlayerSoulChargeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerSoulCharge> PLAYER_SOUL_CHARGE =
            CapabilityManager.get(new CapabilityToken<PlayerSoulCharge>() {
                // Not overriding anything here
            });

    private PlayerSoulCharge soul_charge = null;
    private final LazyOptional<PlayerSoulCharge> optional = LazyOptional.of(this::createPlayerSoulCharge);

    private PlayerSoulCharge createPlayerSoulCharge()
    {
        if (this.soul_charge == null)
        {
            this.soul_charge = new PlayerSoulCharge();
        }

        return this.soul_charge;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SOUL_CHARGE)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSoulCharge().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSoulCharge().loadNBTdata(nbt);
    }
}
