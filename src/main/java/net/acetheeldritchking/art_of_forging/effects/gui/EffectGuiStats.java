package net.acetheeldritchking.art_of_forging.effects.gui;

import net.acetheeldritchking.art_of_forging.ArtOfForging;
import net.acetheeldritchking.art_of_forging.effects.SoulChargedEffect;
import se.mickelus.tetra.effect.ChargedAbilityEffect;
import se.mickelus.tetra.effect.ItemEffect;

public class EffectGuiStats {
    public static final ChargedAbilityEffect[] aofAbilities;

    // Cavalry
    public static final ItemEffect cavalryEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":cavalry");
    public static final String cavalryEffectName =
            ArtOfForging.MOD_ID + ".effect.cavalry.name";
    public static final String cavalryEffectTooltip =
            ArtOfForging.MOD_ID + ".effect.cavalry.tooltip";

    // Evoking Maw
    public static final ItemEffect evokingMaw =
            ItemEffect.get(ArtOfForging.MOD_ID + ":evoking_maw");
    public static final String evokingMawName =
            ArtOfForging.MOD_ID + ".effect.evokingmaw.name";
    public static final String evokingMawTooltip =
            ArtOfForging.MOD_ID + ".effect.evokingmaw.tooltip";

    // Jousting
    public static final ItemEffect dismountingEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":dismounting");
    public static final String dismountingEffectName =
            ArtOfForging.MOD_ID + ".effect.dismounting.name";
    public static final String dismountingEffectTooltip =
            ArtOfForging.MOD_ID + ".effect.dismounting.tooltip";

    // Knockback
    public static final ItemEffect knockbackEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":knockback");
    public static final String knockbackEffectName =
            ArtOfForging.MOD_ID + ".effect.knockback.name";
    public static String knockbackEffectTooltip =
            ArtOfForging.MOD_ID + ".effect.knockback.tooltip";

    // Life Fiber Loss
    public static final ItemEffect lifeFiberLoss =
            ItemEffect.get(ArtOfForging.MOD_ID + ":life_fiber_loss");
    public static final String lifeFiberLossName =
            ArtOfForging.MOD_ID + ".effect.life_fiber_loss.name";
    public static final String lifeFiberLossTooltip =
            ArtOfForging.MOD_ID + ".effect.life_fiber_loss.tooltip";

    // Lifesteal
    public static final ItemEffect lifeSteal =
            ItemEffect.get(ArtOfForging.MOD_ID + ":life_steal");
    public static final String lifeStealName =
            ArtOfForging.MOD_ID + ".effect.life_steal.name";
    public static final String lifeStealTooltip =
            ArtOfForging.MOD_ID + ".effect.life_steal.tooltip";

    // Stormcaller
    public static final ItemEffect stormCaller =
            ItemEffect.get(ArtOfForging.MOD_ID + ":stormcaller");
    public static final String stormCallerName =
            ArtOfForging.MOD_ID + ".effect.stormcaller.name";
    public static final String stormCallerTooltip =
            ArtOfForging.MOD_ID + ".effect.stormcaller.tooltip";

    // Decaying
    public static final ItemEffect decaying =
            ItemEffect.get(ArtOfForging.MOD_ID + ":decaying");
    public static final String decayingName =
            ArtOfForging.MOD_ID + ".effect.decaying.name";
    public static final String decayingTooltip =
            ArtOfForging.MOD_ID + ".effect.decaying.tooltip";

    // Dragon Mist Effect
    public static final ItemEffect dragonMist =
            ItemEffect.get(ArtOfForging.MOD_ID + ":dragon_mist");
    public static final String dragonMistName =
            ArtOfForging.MOD_ID + ".effect.dragon_mist.name";
    public static final String dragonMistTooltip =
            ArtOfForging.MOD_ID + ".effect.dragon_mist.tooltip";

    // Disorienting Effect
    public static final ItemEffect disorientingEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":disorienting");
    public static final String disorientingName =
            ArtOfForging.MOD_ID + ".effect.disorienting.name";
    public static final String disorientingTooltip =
            ArtOfForging.MOD_ID + ".effect.disorienting.tooltip";

    // Vengeance
    public static final ItemEffect vengeanceEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":vengeance");
    public static final String vengeanceName =
            ArtOfForging.MOD_ID + ".effect.vengeance.name";
    public static final String vengeanceTooltip =
            ArtOfForging.MOD_ID + ".effect.vengeance.tooltip";

    // Resolve
    public static final ItemEffect resolveEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":resolve");
    public static final String resolveName =
            ArtOfForging.MOD_ID + ".effect.resolve.name";
    public static final String resolveTooltip =
            ArtOfForging.MOD_ID + ".effect.resolve.tooltip";

    // Devouring
    public static final ItemEffect devouringEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":devouring");
    public static final String devouringEffectName =
            ArtOfForging.MOD_ID + ".effect.devouring.name";
    public static final String devouringEffectTooltip =
            ArtOfForging.MOD_ID + ".effect.devouring.tooltip";

    // Infernal Rebuke
    public static final ItemEffect infernalRebukeEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":infernal_rebuke");
    public static final String infernalRebukeName =
            ArtOfForging.MOD_ID + ".effect.infernal_rebuke.name";
    public static final String infernalRebukeTooltip =
            ArtOfForging.MOD_ID + ".effect.infernal_rebuke.tooltip";

    // Hubris
    public static final ItemEffect hubrisEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":hubris");
    public static final String hubrisName =
            ArtOfForging.MOD_ID + ".effect.hubris.name";
    public static final String hubrisTooltip =
            ArtOfForging.MOD_ID + ".effect.hubris.tooltip";

    // Slaughtering
    public static final ItemEffect slaughteringEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":slaughtering");
    public static final String slaughteringName =
            ArtOfForging.MOD_ID + ".effect.slaughtering.name";
    public static final String slaughteringTooltip =
            ArtOfForging.MOD_ID + ".effect.slaughtering.tooltip";

    // Carnage
    public static final ItemEffect carnageEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":carnage");
    public static final String carnageName =
            ArtOfForging.MOD_ID + ".effect.carnage.name";
    public static final String carnageTooltip =
            ArtOfForging.MOD_ID + ".effect.carnage.tooltip";

    // Judgement
    public static final ItemEffect judgementEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":judgement");
    public static final String judgementName =
            ArtOfForging.MOD_ID + ".effect.judgement.name";
    public static final String judgementTooltip =
            ArtOfForging.MOD_ID + ".effect.judgement.tooltip";

    // Retribution
    public static final ItemEffect retributionEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":retribution");
    public static final String retributionName =
            ArtOfForging.MOD_ID + ".effect.retribution.name";
    public static final String retributionTooltip =
            ArtOfForging.MOD_ID + ".effect.retribution.tooltip";

    // Esoteric Edge
    public static final ItemEffect esotericEdgeEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":esoteric_edge");
    public static final String esotericEdgeName =
            ArtOfForging.MOD_ID + ".effect.esoteric_edge.name";
    public static final String esotericEdgeTooltip =
            ArtOfForging.MOD_ID + ".effect.esoteric_edge.tooltip";

    // Feasting
    public static final ItemEffect feastingEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":feasting");
    public static final String feastingName =
            ArtOfForging.MOD_ID + ".effect.feasting.name";
    public static final String feastingTooltip =
            ArtOfForging.MOD_ID + ".effect.feasting.tooltip";

    // Nano-Fused
    public static final ItemEffect nanoFusedEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":nano_fused");
    public static final String nanoFusedName =
            ArtOfForging.MOD_ID + ".effect.nano_fused.name";
    public static final String nanoFusedTooltip =
            ArtOfForging.MOD_ID + ".effect.nano_fused.tooltip";

    // Beheading
    public static final ItemEffect beheadingEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":beheading");
    public static final String beheadingName =
            ArtOfForging.MOD_ID + ".effect.beheading.name";
    public static final String beheadingTooltip =
            ArtOfForging.MOD_ID + ".effect.beheading.tooltip";

    // Soul Charged
    public static final ItemEffect soulChargedEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":soul_charged");
    public static final String soulChargedName =
            ArtOfForging.MOD_ID + ".effect.soul_charged.name";
    public static final String soulChargedTooltip =
            ArtOfForging.MOD_ID + ".effect.soul_charged.tooltip";

    // Sonic Shock
    public static final ItemEffect sonicShockEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":sonic_shock");
    public static final String sonicShockName =
            ArtOfForging.MOD_ID + ".effect.sonic_shock.name";
    public static final String sonicShockTooltip =
            ArtOfForging.MOD_ID + ".effect.sonic_shock.tooltip";


    // CURIO EFFECTS //
    // Venom protection
    public static final ItemEffect venomProtectionEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":venom_protection");
    public static final String venomProtectionName =
            ArtOfForging.MOD_ID + ".effect.venom_protection.name";
    public static final String venomProtectionTooltip =
            ArtOfForging.MOD_ID + ".effect.venom_protection.tooltip";

    // Flame protection
    public static final ItemEffect flameProtectionEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":flame_protection");
    public static final String flameProtectionName =
            ArtOfForging.MOD_ID + ".effect.flame_protection.name";
    public static final String flameProtectionTooltip =
            ArtOfForging.MOD_ID + ".effect.flame_protection.tooltip";

    // Arcane protection
    public static final ItemEffect arcaneProtectionEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":arcane_protection");
    public static final String arcaneProtectionName =
            ArtOfForging.MOD_ID + ".effect.arcane_protection.name";
    public static final String arcaneProtectionTooltip =
            ArtOfForging.MOD_ID + ".effect.arcane_protection.tooltip";

    // Wisdom infused
    public static final ItemEffect wisdomInfusedEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":wisdom_infused");
    public static final String wisdomInfusedName =
            ArtOfForging.MOD_ID + ".effect.wisdom_infused.name";
    public static final String wisdomInfusedTooltip =
            ArtOfForging.MOD_ID + ".effect.wisdom_infused.tooltip";

    // Strength infused
    public static final ItemEffect strengthInfusedEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":strength_infused");
    public static final String strengthInfusedName =
            ArtOfForging.MOD_ID + ".effect.strength_infused.name";
    public static final String strengthInfusedTooltip =
            ArtOfForging.MOD_ID + ".effect.strength_infused.tooltip";

    // Healing infused
    public static final ItemEffect healingInfusedEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":healing_infused");
    public static final String healingInfusedName =
            ArtOfForging.MOD_ID + ".effect.healing_infused.name";
    public static final String healingInfusedTooltip =
            ArtOfForging.MOD_ID + ".effect.healing_infused.tooltip";

    // Karma infused
    public static final ItemEffect karmaInfusedEffect =
            ItemEffect.get(ArtOfForging.MOD_ID + ":karma_infused");
    public static final String karmaInfusedName =
            ArtOfForging.MOD_ID + ".effect.karma_infused.name";
    public static final String karmaInfusedTooltip =
            ArtOfForging.MOD_ID + ".effect.karma_infused.tooltip";


    // Charged Abilities
    static {
        aofAbilities = new ChargedAbilityEffect[]{
                SoulChargedEffect.instance
        };
    }
}
