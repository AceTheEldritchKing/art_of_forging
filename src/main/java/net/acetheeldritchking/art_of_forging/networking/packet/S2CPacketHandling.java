package net.acetheeldritchking.art_of_forging.networking.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class S2CPacketHandling {

    public static ClientLevel getLevel()
    {
        ClientLevel level = Minecraft.getInstance().level;
        return level;
    }
}
