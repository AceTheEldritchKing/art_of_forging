package net.acetheeldritchking.art_of_forging.capabilities.subjugation;

import net.minecraft.nbt.CompoundTag;

public class PlayerSubjugation {
    private int subjugation;
    private final int MAX_SUBJUGATION = 10;
    private final int MIN_SUBJUGATION = 0;

    public int getSubjugation() {
        return subjugation;
    }

    public int setSubjugation(int set) {
        this.subjugation = set;
        return subjugation;
    }

    public void resetSubjugation() {
        this.subjugation = 0;
    }

    public void addSubjugation(int add) {
        this.subjugation = Math.min(subjugation + add, MAX_SUBJUGATION);
    }

    public void subSubjugation(int sub) {
        this.subjugation = Math.max(subjugation - sub, MIN_SUBJUGATION);
    }

    public void copyFrom(PlayerSubjugation source) {
        this.subjugation = source.subjugation;
    }

    public void saveNBTdata(CompoundTag nbt) {
        nbt.putInt("subjugation", subjugation);
    }

    public void loadNBTdata(CompoundTag nbt) {
        subjugation = nbt.getInt("subjugation");
    }
}
