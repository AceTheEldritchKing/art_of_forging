package net.acetheeldritchking.art_of_forging.effects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.effect.AbilityUseResult;
import se.mickelus.tetra.effect.ChargedAbilityEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class SonicShockEffect extends ChargedAbilityEffect {
    public static final SonicShockEffect instance = new SonicShockEffect();

    public SonicShockEffect() {
        super(60, 0.15D, 450, 1.0D, sonicShockEffect, TargetRequirement.either, UseAnim.SPEAR, "raised");
    }

    @OnlyIn(Dist.CLIENT)
    public static void init()
    {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(sonicShockEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, sonicShockName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.integerLabel, new TooltipGetterInteger
                        (sonicShockTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @Override
    public void perform(Player attacker, InteractionHand hand, ItemModularHandheld item, ItemStack itemStack, LivingEntity target, Vec3 hitVec, int chargedTicks) {
        if (!target.level.isClientSide)
        {
            // Sonic Boom damage
            int level = item.getEffectLevel(itemStack, sonicShockEffect);

            AbilityUseResult result = this.doSonicBoomAttack(level, attacker, item, itemStack, target);

            attacker.swing(hand, false);
            attacker.getCooldowns().addCooldown(item, this.getCooldown(item, itemStack));
            item.tickProgression(attacker, itemStack, 1);
            item.applyDamage(10, itemStack, attacker);
        }

        //super.perform(attacker, hand, item, itemStack, targetPos, hitVec, chargedTicks);
    }

    // Literally just sonic boom code, don't you dare judge me
    private AbilityUseResult doSonicBoomAttack(float damage, Player attacker, ItemModularHandheld item, ItemStack itemStack, LivingEntity target)
    {
        AbilityUseResult result = item.hitEntity(itemStack, attacker, target, 0, 1, 1, 1);
        ServerLevel world = (ServerLevel) attacker.level;

        if (result != AbilityUseResult.fail)
        {
            Vec3 vec3 = attacker.position().add(0.0D, (double)1.6F, 0.0D);
            Vec3 vec31 = target.getEyePosition().subtract(vec3);
            Vec3 vec32 = vec31.normalize();

            for (int i = 1; i < Mth.floor(vec31.length()) + 7; i++)
            {
                Vec3 vec33 = vec3.add(vec32.scale(i));
                world.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z,
                        1, 0.0D, 0.0D, 0.0D, 0.0D);
            }

            attacker.level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(),
                    SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 3.0F, 1.0F);
            target.hurt(DamageSource.sonicBoom(attacker), damage);

            double d1 = 0.5 * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            double d0 = 2.5 * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
            target.push(vec32.x() * d0, vec32.y() * d1, vec32.z() * d0);
        }
        return result;
    }
}
