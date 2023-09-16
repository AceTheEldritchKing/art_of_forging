package net.acetheeldritchking.art_of_forging.networking.packet;

import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LifeStealParticlesS2CPacket {
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
            Level world = S2CPacketHandling.getLevel();

            double radius = 0.7;
            double angleIncrement = 2.0 * Math.toRadians(0.5 / radius);
            int speedFactor = 10;

            for (double angle = 0; angle < Math.PI * 2; angle += angleIncrement)
            {
                double offsetX = radius * Math.sin(angle);
                double offsetZ = radius * Math.cos(angle);

                double targetX = this.xPos + offsetX;
                double targetY = this.yPos;
                double targetZ = this.zPos + offsetZ;

                world.addParticle(new DustColorTransitionOptions
                                (new Vector3f(0.78F, 0.18F, 0.18F),
                                        new Vector3f(0.0F, 0.0F, 0.0F), 1.0F),
                        targetX, targetY, targetZ,
                        offsetX*speedFactor, 0.15D, offsetZ*speedFactor);

                /*if (angle % 20 == 0)
                {
                world.sendParticles(new DustColorTransitionOptions
                                (new Vector3f(0.78F, 0.18F, 0.18F), new Vector3f(0.0F, 0.0F, 0.0F), 1.0F),
                        targetX, targetY, targetZ, 15, 0.15D, 0.15D, 0.15D, 0.15D);

                }*/

            /*
            world.sendParticles(ParticleTypes.SCULK_SOUL,
                    targetX, targetY, targetZ, 15,
                    0.15D, 0.15D, 0.15D, 0.15D);
            */
            }
        });
        return true;
    }
}
