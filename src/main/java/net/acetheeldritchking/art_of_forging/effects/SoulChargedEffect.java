package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.capabilities.soulCharge.PlayerSoulChargeProvider;
import net.acetheeldritchking.art_of_forging.networking.AoFPackets;
import net.acetheeldritchking.art_of_forging.networking.packet.SoulChargedParticlesS2CPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.effect.AbilityUseResult;
import se.mickelus.tetra.effect.ChargedAbilityEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.List;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class SoulChargedEffect extends ChargedAbilityEffect {
    public static final SoulChargedEffect instance = new SoulChargedEffect();

    public SoulChargedEffect() {
        super(20, 0.15D, 50, 8.0D, soulChargedEffect, TargetRequirement.either, UseAnim.BOW, "raised");
    }

    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(soulChargedEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, soulChargedName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (soulChargedTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    // Gain soul charge points
    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker)
        {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item)
            {
                // Effect
                int level = item.getEffectLevel(heldStack, soulChargedEffect);

                if (level >= 2 && attacker instanceof Player player)
                {
                    player.getCapability(PlayerSoulChargeProvider.PLAYER_SOUL_CHARGE).ifPresent(soul_charge -> {
                        soul_charge.addSoulCharge(1);
                        // System.out.println("Added: " + soul_charge.getSoulCharge());

                        if (soul_charge.getSoulCharge() >= 5)
                        {
                            player.level.playSound(null, player.getX(), player.getY(), player.getZ(),
                                    SoundEvents.BLAZE_HURT, SoundSource.PLAYERS, 1.0F, 0.8F);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void perform(Player attacker, InteractionHand hand, ItemModularHandheld item, ItemStack itemStack, LivingEntity target, Vec3 hitVec, int chargedTicks) {
        if (!target.level.isClientSide())
        {
            attacker.getCapability(PlayerSoulChargeProvider.PLAYER_SOUL_CHARGE).ifPresent(soul_charge -> {
                // AoE
                double radius = 5;
                double height = 2;

                // Particles & Sounds
                double attackerX = attacker.getX();
                double attackerY = attacker.getY(0.5D);
                double attackerZ = attacker.getZ();

                // Seconds on fire
                int level = item.getEffectLevel(itemStack, soulChargedEffect);

                // Magic damage
                float eff = item.getEffectEfficiency(itemStack, soulChargedEffect);

                if (soul_charge.getSoulCharge() >= 5)
                {
                    // System.out.println("Do actual shit????");

                    // reset
                    soul_charge.resetSoulCharge();

                    AbilityUseResult result = this.doAoeAttack(radius, height, radius, level*2, eff,
                            attacker, hand, item, itemStack, target, hitVec);
                    this.playSoundsAndParticles(attackerX, attackerY, attackerZ, (ServerPlayer) attacker);
                }
            });
        }

        attacker.swing(hand, false);
        attacker.getCooldowns().addCooldown(item, this.getCooldown(item, itemStack));
        item.tickProgression(attacker, itemStack, 2);
        item.applyDamage(2, itemStack, attacker);
        //super.perform(attacker, hand, item, itemStack, target, hitVec, chargedTicks);
    }

    // Actual AoE
    private AbilityUseResult doAoeAttack(double x, double y, double z, int seconds, float damage, Player attacker, InteractionHand hand, ItemModularHandheld item, ItemStack itemStack, LivingEntity target, Vec3 hitVec)
    {
        // System.out.println("Does it go to function?");
        AbilityUseResult result = item.hitEntity(itemStack, attacker, target, 1, 1, 1, 1);

        if (result != AbilityUseResult.fail)
        {
            // System.out.println("Does it actually do the effect?");
            AABB aoeAttack = new AABB(hitVec, hitVec).inflate(x*2, y, z*2);
            List<LivingEntity> targets = (List)attacker.level.getEntities(attacker, aoeAttack);

            for (LivingEntity livingTargets : targets)
            {
                // System.out.println("Loop, brother");
                livingTargets.setSecondsOnFire(seconds);
                livingTargets.hurt(DamageSource.MAGIC, damage);
            }
        }
        return result;
    }

    // Particles around player once perform is ready
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
    {
        ItemStack heldStack = event.player.getMainHandItem();

        if (heldStack.getItem() instanceof  ModularItem item)
        {
            // Ensures particle effects
            int level = item.getEffectLevel(heldStack, soulChargedEffect);

            // Coords
            double posX = event.player.getX();
            double posY = event.player.getY(0.5D);
            double posZ = event.player.getZ();

            if (level >= 2)
            {
                event.player.getCapability(PlayerSoulChargeProvider.PLAYER_SOUL_CHARGE).ifPresent(soul_charge -> {
                    if (soul_charge.getSoulCharge() >= 5)
                    {
                        ServerLevel world = (ServerLevel) event.player.level;
                        world.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, posX, posY, posZ,
                                1, 0.5D, 0.5D, 0.5D, 0.0D);
                    }
                });
            }
        }
    }

    // Sound and Effects
    private void playSoundsAndParticles(double x, double y, double z, ServerPlayer player)
    {
        AoFPackets.sendToPlayer(new SoulChargedParticlesS2CPacket(x, y, z), player);
        player.level.playSound(null, x, y, z, SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }
}
