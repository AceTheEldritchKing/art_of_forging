package net.acetheeldritchking.art_of_forging;

import net.acetheeldritchking.art_of_forging.network.ClientProxy;
import net.acetheeldritchking.art_of_forging.network.CommonProxy;
import net.acetheeldritchking.art_of_forging.network.PacketHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArtOfForging.MOD_ID)
public class ArtOfForging
{
    public static final String MOD_ID = "art_of_forging";
    public static CommonProxy PROXY;
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public ArtOfForging() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        //Test comment for first commit+push
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
       Config.init(event.getServer().getWorldPath(new LevelResource("bettercombatnbt")));
    }

    @SubscribeEvent
    public void onPlayerConnect(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer() instanceof ServerPlayer) {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), Config.configFileToSSyncConfig());
        }
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
