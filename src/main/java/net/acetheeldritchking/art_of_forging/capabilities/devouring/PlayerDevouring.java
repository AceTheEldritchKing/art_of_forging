package net.acetheeldritchking.art_of_forging.capabilities.devouring;

import net.minecraft.nbt.CompoundTag;

public class PlayerDevouring {
    private int devour;
    private final int MAX_DEVOUR = 30;
    private final int MIN_DEVOUR = 0;

    public int getDevour() {
        return devour;
    }

    public void addDevour(int add) {
        this.devour = Math.min(devour + add, MAX_DEVOUR);
    }

    public void subDevour(int sub) {
        this.devour = Math.max(devour - sub, MIN_DEVOUR);
    }

    public int setDevour(int set) {
        this.devour = set;
        return devour;
    }

    public void resetDevour() {
        this.devour = 0;
    }

    public void copyFrom(PlayerDevouring source) {
        this.devour = source.devour;
    }

    public void saveNBTdata(CompoundTag nbt) {
        nbt.putInt("devouring", devour);
    }

    public void loadNBTdata(CompoundTag nbt) {
        devour = nbt.getInt("devouring");
    }
}
