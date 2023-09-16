package net.acetheeldritchking.art_of_forging.events;

import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.acetheeldritchking.art_of_forging.capabilities.carnage.PlayerCarnage;
import net.acetheeldritchking.art_of_forging.capabilities.carnage.PlayerCarnageProvider;
import net.acetheeldritchking.art_of_forging.capabilities.devouring.PlayerDevouring;
import net.acetheeldritchking.art_of_forging.capabilities.devouring.PlayerDevouringProvider;
import net.acetheeldritchking.art_of_forging.capabilities.soulCharge.PlayerSoulCharge;
import net.acetheeldritchking.art_of_forging.capabilities.soulCharge.PlayerSoulChargeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ArtOfForging.MOD_ID)
public class AoFEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            // Devouring
            if(!event.getObject().getCapability(PlayerDevouringProvider.PLAYER_DEVOURING).isPresent()) {
                event.addCapability(new ResourceLocation(ArtOfForging.MOD_ID, "devouring"), new PlayerDevouringProvider());
            }

            // Carnage
            if (!event.getObject().getCapability(PlayerCarnageProvider.PLAYER_CARNAGE).isPresent()) {
                event.addCapability(new ResourceLocation(ArtOfForging.MOD_ID, "carnage"), new PlayerCarnageProvider());
            }

            // Soul Charged
            if (!event.getObject().getCapability(PlayerSoulChargeProvider.PLAYER_SOUL_CHARGE).isPresent()) {
                event.addCapability(new ResourceLocation(ArtOfForging.MOD_ID, "soul_charge"), new PlayerSoulChargeProvider());
            }

            // Karma
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            // Devouring
            event.getOriginal().getCapability(PlayerDevouringProvider.PLAYER_DEVOURING).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerDevouringProvider.PLAYER_DEVOURING).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });

            // Carnage
            /*event.getOriginal().getCapability(PlayerCarnageProvider.PLAYER_CARNAGE).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerCarnageProvider.PLAYER_CARNAGE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });*/

            // Soul Charged

            // Karma
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        // Devouring
        event.register(PlayerDevouring.class);

        // Carnage
        event.register(PlayerCarnage.class);

        // Soul Charge
        event.register(PlayerSoulCharge.class);

        // Karma
    }

}
