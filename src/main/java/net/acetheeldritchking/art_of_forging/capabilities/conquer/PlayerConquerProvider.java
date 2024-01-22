package net.acetheeldritchking.art_of_forging.capabilities.conquer;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerConquerProvider implements ICapabilityProvider, INBTSerializable {
    public static Capability<PlayerConquer> PLAYER_CONQUER =
            CapabilityManager.get(new CapabilityToken<PlayerConquer>() {
                // Not overriding anything here
            });

    private PlayerConquer conquer = null;
    private final LazyOptional<PlayerConquer> optional = LazyOptional.of(this::createPlayerConquer);

    private PlayerConquer createPlayerConquer()
    {
        if (this.conquer == null)
        {
            this.conquer = new PlayerConquer();
        }

        return this.conquer;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_CONQUER)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerConquer().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(Tag nbt) {

    }
}
