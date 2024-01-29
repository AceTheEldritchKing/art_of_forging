package net.acetheeldritchking.art_of_forging.networking.packet;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LifeStealPacketHandler {
    public static void doLifestealParticles(double xPos, double yPos, double zPos)
    {
        Level world = Minecraft.getInstance().level;
        if (world != null)
        {
            double radius = 0.7;
            double angleIncrement = 2.0 * Math.toRadians(0.5 / radius);
            int speedFactor = 10;

            for (double angle = 0; angle < Math.PI * 2; angle += angleIncrement) {
                double offsetX = radius * Math.sin(angle);
                double offsetZ = radius * Math.cos(angle);

                double targetX = xPos + offsetX;
                double targetY = yPos;
                double targetZ = zPos + offsetZ;

                world.addParticle(new DustColorTransitionOptions
                                (new Vector3f(0.78F, 0.18F, 0.18F),
                                        new Vector3f(0.0F, 0.0F, 0.0F), 1.0F),
                        targetX, targetY, targetZ,
                        offsetX * speedFactor, 0.15D, offsetZ * speedFactor);
            }
        }
    }
}
