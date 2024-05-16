package net.acetheeldritchking.art_of_forging.effects;

import net.acetheeldritchking.art_of_forging.capabilities.subjugation.PlayerSubjugationProvider;
import net.acetheeldritchking.art_of_forging.effects.potion.PotionEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
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

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class SubjugationEffect extends ChargedAbilityEffect {
    public static final SubjugationEffect instance = new SubjugationEffect();

    public SubjugationEffect() {
        super(20, 0.15D, 100, 1.0D, subjugationEffect, TargetRequirement.entity, UseAnim.SPEAR, "raised");
    }

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(subjugationEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, subjugationName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (subjugationTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    // Gain points on hit
    @SubscribeEvent
    public void onLivingAttackEvent(LivingDamageEvent event) {
        Entity attackingEntity = event.getSource().getEntity();
        LivingEntity target = event.getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Init effect
                int level = item.getEffectLevel(heldStack, subjugationEffect);

                // Potion effect duration
                int eff = (int) item.getEffectEfficiency(heldStack, subjugationEffect);

                if (level > 0 && !attacker.level().isClientSide() && attacker instanceof Player player) {
                    player.getCapability(PlayerSubjugationProvider.PLAYER_SUBJUGATION).ifPresent(subjugation ->
                    {
                        // System.out.println("Added subjugation, current level is: " + subjugation.getSubjugation());

                        subjugation.addSubjugation(1);

                        if (subjugation.getSubjugation() == 10) {
                            target.addEffect(new MobEffectInstance(PotionEffects.TARGETED.get(), eff * 20, 0,
                                    false, false, true));
                        }
                    });
                }
            }
        }
    }

    // Charged effect
    @Override
    public void perform(Player attacker, InteractionHand hand, ItemModularHandheld item, ItemStack itemStack, LivingEntity target, Vec3 hitVec, int chargedTicks) {
        if (!target.level().isClientSide()) {
            attacker.getCapability(PlayerSubjugationProvider.PLAYER_SUBJUGATION).ifPresent(subjugation ->
            {
                // Particles & Sounds
                double attackerX = attacker.getX();
                double attackerY = attacker.getY(0.5D);
                double attackerZ = attacker.getZ();

                // Amount of damage
                int level = item.getEffectLevel(itemStack, subjugationEffect);

                // Duration of effects
                int eff = (int) item.getEffectEfficiency(itemStack, subjugationEffect);

                if (subjugation.getSubjugation() == 10 && target.hasEffect(PotionEffects.TARGETED.get())) {
                    // Reset
                    subjugation.resetSubjugation();

                    AbilityUseResult result = this.performAttack(attacker, item, itemStack, target, eff, level);
                    this.playSounds(attackerX, attackerY, attackerZ, (ServerPlayer) attacker);
                }
            });
        }

        attacker.swing(hand, false);
        attacker.getCooldowns().addCooldown(item, this.getCooldown(item, itemStack));
        item.tickProgression(attacker, itemStack, 2);
        item.applyDamage(2, itemStack, attacker);
        // super.perform(attacker, hand, item, itemStack, target, hitVec, chargedTicks);
    }

    // Actual Effect
    // If reached max points, apply Targeted effect
    // If entity has Targeted, deal damage & debuffs
    // Weakness, slowness, blindness.
    private AbilityUseResult performAttack(Player attacker, ItemModularHandheld item, ItemStack itemStack, LivingEntity target, int duration, float damage) {
        AbilityUseResult result = item.hitEntity(itemStack, attacker, target, 0, 0F, 0F);

        if (result != AbilityUseResult.fail) {
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration * 20, 1,
                    true, true, true));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration * 20, 1,
                    true, true, true));
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, duration * 20, 1,
                    true, true, true));

            target.hurt(target.damageSources().magic(), damage);
            attacker.heal(5);
        }

        return result;
    }

    // Particles around player once perform is ready
    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
        ItemStack heldStack = event.player.getMainHandItem();

        if (heldStack.getItem() instanceof ModularItem item) {
            // Ensures particle effects
            int level = item.getEffectLevel(heldStack, subjugationEffect);

            // Coords
            double posX = event.player.getX();
            double posY = event.player.getY(0.5D);
            double posZ = event.player.getZ();

            if (level >= 2) {
                event.player.getCapability(PlayerSubjugationProvider.PLAYER_SUBJUGATION).ifPresent(subjugation -> {
                    if (subjugation.getSubjugation() >= 10) {
                        ServerLevel world = (ServerLevel) event.player.level();
                        world.sendParticles(ParticleTypes.CRIMSON_SPORE, posX, posY, posZ,
                                1, 0.1D, 0.1D, 0.1D, 0.0D);
                    }
                });
            }
        }
    }

    // Play sounds
    private void playSounds(double x, double y, double z, ServerPlayer player) {
        player.level().playSound(null, x, y, z, SoundEvents.WITHER_HURT, SoundSource.PLAYERS, 1.0F, 0.5F);
    }
}
