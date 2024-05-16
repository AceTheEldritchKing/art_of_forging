package net.acetheeldritchking.art_of_forging.networking;

import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.acetheeldritchking.art_of_forging.networking.packet.LifeStealParticlesS2CPacket;
import net.acetheeldritchking.art_of_forging.networking.packet.SoulChargedParticlesS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class AoFPackets {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(ArtOfForging.MOD_ID,
                        "aof_packets"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        // Server to Client

        // Life Steal Circle Particles
        net.messageBuilder(LifeStealParticlesS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LifeStealParticlesS2CPacket::new)
                .encoder(LifeStealParticlesS2CPacket::toBytes)
                .consumerMainThread(LifeStealParticlesS2CPacket::handle)
                .add();

        // Soul Charge Circle Particles
        net.messageBuilder(SoulChargedParticlesS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SoulChargedParticlesS2CPacket::new)
                .encoder(SoulChargedParticlesS2CPacket::toBytes)
                .consumerMainThread(SoulChargedParticlesS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToClient(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MGS> void sendToEntity(MGS message, LivingEntity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), message);
    }
}
