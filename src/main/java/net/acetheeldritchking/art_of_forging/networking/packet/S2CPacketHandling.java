package net.acetheeldritchking.art_of_forging.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class S2CPacketHandling {

    public static ClientLevel getLevel()
    {
        ClientLevel level = Minecraft.getInstance().level;
        return level;
    }
}
