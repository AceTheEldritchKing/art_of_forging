package net.acetheeldritchking.art_of_forging.capabilities.karma;

import net.minecraft.nbt.CompoundTag;

public class PlayerKarma {
    private int karma;
    private final int MAX_KARMA = 5;
    private final int MIN_KARMA = -5;

    public int getKarma() {
        return karma;
    }

    public int setKarma(int set) {
        this.karma = set;
        return karma;
    }

    public void resetKarma() {
        this.karma = 0;
    }

    public void addKarma(int add) {
        this.karma = Math.min(karma + add, MAX_KARMA);
    }

    public void subKarma(int sub) {
        this.karma = Math.max(karma - sub, MIN_KARMA);
    }

    public void copyFrom(PlayerKarma source) {
        this.karma = source.karma;
    }

    public void saveNBTdata(CompoundTag nbt) {
        nbt.putInt("karma", karma);
    }

    public void loadNBTdata(CompoundTag nbt) {
        karma = nbt.getInt("karma");
    }
}
