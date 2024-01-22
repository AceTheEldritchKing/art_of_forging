package net.acetheeldritchking.art_of_forging.capabilities.conquer;

import net.minecraft.nbt.CompoundTag;

public class PlayerConquer {
    private int conquer;
    private final int MAX_CONQUER = 5;
    private final int MIN_CONQUER = 0;

    public int getConquer()
    {
        return conquer;
    }

    public int setConquer(int set)
    {
        this.conquer = set;
        return conquer;
    }

    public void resetConquer()
    {
        this.conquer = 0;
    }

    public void addConquer(int add)
    {
        this.conquer = Math.min(conquer + add, MAX_CONQUER);
    }

    public void subConquer(int sub)
    {
        this.conquer = Math.max(conquer - sub, MIN_CONQUER);
    }

    public void copyFrom(PlayerConquer source)
    {
        this.conquer = source.conquer;
    }

    public void saveNBTdata(CompoundTag nbt)
    {
        nbt.putInt("conquer", conquer);
    }

    public void loadNBTdata(CompoundTag nbt)
    {
        conquer = nbt.getInt("conquer");
    }
}
