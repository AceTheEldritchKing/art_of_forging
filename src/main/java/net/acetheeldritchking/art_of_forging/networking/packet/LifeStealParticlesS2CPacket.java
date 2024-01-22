package net.acetheeldritchking.art_of_forging.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LifeStealParticlesS2CPacket extends LifeStealPacketHandler {
    private final double xPos;
    private final double yPos;
    private final double zPos;

    public LifeStealParticlesS2CPacket(double xPos, double yPos, double zPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public LifeStealParticlesS2CPacket(FriendlyByteBuf buf)
    {
        this.xPos = buf.readDouble();
        this.yPos = buf.readDouble();
        this.zPos = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeDouble(xPos);
        buf.writeDouble(yPos);
        buf.writeDouble(zPos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();

        context.enqueueWork(() -> {
            // Server to Client
            doLifestealParticles(this.xPos, this.yPos, this.zPos);
        });
        return true;
    }
}
