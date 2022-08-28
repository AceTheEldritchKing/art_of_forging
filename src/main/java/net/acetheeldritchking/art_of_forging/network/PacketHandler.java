package net.acetheeldritchking.art_of_forging.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "3.2.1";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("fightnbtintegration", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);
    private static int ID = 0;

    public static void init() {
        registerPacket(SSyncConfig.class, pb -> new SSyncConfig().readPacketData(pb));
    }

    public static <T extends IPacket> void registerPacket(Class<T> packetClass, Function<FriendlyByteBuf,T> decoder) {
        CHANNEL.registerMessage(
                ID++,
                packetClass,
                IPacket::writePacketData,
                decoder,
                (msg, ctx) -> {
                    ctx.get().enqueueWork(() -> msg.processPacket(ctx.get()));
                    ctx.get().setPacketHandled(true);
                }
        );
    }
}
