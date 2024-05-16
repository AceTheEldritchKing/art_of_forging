package net.acetheeldritchking.art_of_forging.capabilities.soulCharge;

import net.minecraft.nbt.CompoundTag;

public class PlayerSoulCharge {
    private int soul_charge;
    private final int MAX_CHARGE = 5;
    private final int MIN_CHARGE = 0;

    public int getSoulCharge() {
        return soul_charge;
    }

    public int setSoulCharge(int set) {
        this.soul_charge = set;
        return soul_charge;
    }

    public void resetSoulCharge() {
        this.soul_charge = 0;
    }

    public void addSoulCharge(int add) {
        this.soul_charge = Math.min(soul_charge + add, MAX_CHARGE);
    }

    public void subSoulCharge(int sub) {
        this.soul_charge = Math.max(soul_charge + sub, MIN_CHARGE);
    }

    public void copyFrom(PlayerSoulCharge source) {
        this.soul_charge = source.soul_charge;
    }

    public void saveNBTdata(CompoundTag nbt) {
        nbt.putInt("soul_charge", soul_charge);
    }

    public void loadNBTdata(CompoundTag nbt) {
        soul_charge = nbt.getInt("soul_charge");
    }
}
