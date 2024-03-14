package net.acetheeldritchking.art_of_forging.effects;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterInteger;
import se.mickelus.tetra.items.modular.ModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static net.acetheeldritchking.art_of_forging.effects.gui.EffectGuiStats.*;
import static se.mickelus.tetra.gui.stats.StatsHelper.barLength;

public class BeheadingEffect {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        final IStatGetter effectStatGetter = new StatGetterEffectLevel(beheadingEffect, 1);
        final GuiStatBar effectBar = new GuiStatBar
                (0, 0, barLength, beheadingName, 0, 30, false, effectStatGetter,
                        LabelGetterBasic.percentageLabel, new TooltipGetterInteger
                        (beheadingTooltip, effectStatGetter));
        WorkbenchStatsGui.addBar(effectBar);
        HoloStatsGui.addBar(effectBar);
    }

    @SubscribeEvent
    public void onLivingDropEvent(LivingDropsEvent event) {
        LivingEntity target = event.getEntity();
        Entity attackingEntity = event.getSource().getEntity();

        if (attackingEntity instanceof LivingEntity attacker) {
            ItemStack heldStack = attacker.getMainHandItem();

            if (heldStack.getItem() instanceof ModularItem item) {
                // Chance to have head drops
                float level = item.getEffectLevel(heldStack, beheadingEffect);

                // Actual chance
                float chance = level / 100;

                if (level > 0) {
                    // For the loot drop
                    ItemStack headDrop = ItemStack.EMPTY;

                    if (target instanceof Zombie) headDrop = new ItemStack(Items.ZOMBIE_HEAD);
                    else if (target instanceof Creeper) headDrop = new ItemStack(Items.CREEPER_HEAD);
                    else if (target instanceof Skeleton) headDrop = new ItemStack(Items.SKELETON_SKULL);
                    else if (target instanceof WitherSkeleton || target instanceof WitherBoss)
                        headDrop = new ItemStack(Items.WITHER_SKELETON_SKULL);
                    else if (target instanceof EnderDragon) headDrop = new ItemStack(Items.DRAGON_HEAD);
                    else if (target instanceof Player player) {
                        // Looked at how Tetra Pak did this
                        headDrop = new ItemStack(Items.PLAYER_HEAD);
                        GameProfile profile = player.getGameProfile();
                        headDrop.getOrCreateTag().put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), profile));
                    }

                    // Drop
                    if (!headDrop.isEmpty()) {
                        boolean drop = target.level().random.nextFloat() < chance;

                        if (drop) {
                            ItemEntity itemDrop = new ItemEntity
                                    (target.level(), target.getX(), target.getY(), target.getZ(), headDrop);
                            itemDrop.setDefaultPickUpDelay();
                            event.getDrops().add(itemDrop);
                        }
                    }
                }
            }
        }
    }
}
