package net.acetheeldritchking.art_of_forging.capabilities.carnage;

import net.minecraft.nbt.CompoundTag;

public class PlayerCarnage {
    private int carnage;
    private final int MAX_CARNAGE = 5;
    private final int MIN_CARNAGE = 0;

    public int getCarnage() {
        return carnage;
    }

    public int setCarnage(int set) {
        this.carnage = set;
        return carnage;
    }

    public void resetCarnage() {
        this.carnage = 0;
    }

    public void addCarnage(int add) {
        this.carnage = Math.min(carnage + add, MAX_CARNAGE);
    }

    public void subCarnage(int sub) {
        this.carnage = Math.max(carnage - sub, MIN_CARNAGE);
    }

    public void copyFrom(PlayerCarnage source) {
        this.carnage = source.carnage;
    }

    public void saveNBTdata(CompoundTag nbt) {
        nbt.putInt("carnage", carnage);
    }

    public void loadNBTdata(CompoundTag nbt) {
        carnage = nbt.getInt("carnage");
    }
}
