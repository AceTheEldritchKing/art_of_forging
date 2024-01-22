package net.acetheeldritchking.art_of_forging.events;

import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.acetheeldritchking.art_of_forging.capabilities.carnage.PlayerCarnage;
import net.acetheeldritchking.art_of_forging.capabilities.carnage.PlayerCarnageProvider;
import net.acetheeldritchking.art_of_forging.capabilities.conquer.PlayerConquer;
import net.acetheeldritchking.art_of_forging.capabilities.conquer.PlayerConquerProvider;
import net.acetheeldritchking.art_of_forging.capabilities.devouring.PlayerDevouring;
import net.acetheeldritchking.art_of_forging.capabilities.devouring.PlayerDevouringProvider;
import net.acetheeldritchking.art_of_forging.capabilities.karma.PlayerKarma;
import net.acetheeldritchking.art_of_forging.capabilities.karma.PlayerKarmaProvider;
import net.acetheeldritchking.art_of_forging.capabilities.soulCharge.PlayerSoulCharge;
import net.acetheeldritchking.art_of_forging.capabilities.soulCharge.PlayerSoulChargeProvider;
import net.acetheeldritchking.art_of_forging.capabilities.subjugation.PlayerSubjugation;
import net.acetheeldritchking.art_of_forging.capabilities.subjugation.PlayerSubjugationProvider;
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
            if (!event.getObject().getCapability(PlayerKarmaProvider.PLAYER_KARMA).isPresent()) {
                event.addCapability(new ResourceLocation(ArtOfForging.MOD_ID, "karma"), new PlayerKarmaProvider());
            }

            // Conquer
            if (!event.getObject().getCapability(PlayerConquerProvider.PLAYER_CONQUER).isPresent()) {
                event.addCapability(new ResourceLocation(ArtOfForging.MOD_ID, "conquer"), new PlayerConquerProvider());
            }

            // Subjugation
            if (!event.getObject().getCapability(PlayerSubjugationProvider.PLAYER_SUBJUGATION).isPresent()) {
                event.addCapability(new ResourceLocation(ArtOfForging.MOD_ID, "subjugation"), new PlayerSubjugationProvider());
            }
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
        event.register(PlayerKarma.class);

        // Conquer
        event.register(PlayerConquer.class);

        // Subjugation
        event.register(PlayerSubjugation.class);
    }

}
