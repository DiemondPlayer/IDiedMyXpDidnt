package net.diemond_player.idmxd.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.diemond_player.idmxd.util.IDMXDUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public int experienceLevel;

    @Shadow public float experienceProgress;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(at = @At(value = "RETURN"), method = "getXpToDrop")
    private int idmxd$getXpToDrop(int original) {
        if(original!=0) {
            return IDMXDUtils.countXp(this.experienceLevel, this.experienceProgress);
        }else{
            return original;
        }
    }

    @Override
    protected void dropXp() {
        if (this.getWorld() instanceof ServerWorld && !this.isExperienceDroppingDisabled() && (this.shouldAlwaysDropXp() || this.playerHitTimer > 0 && this.shouldDropXp() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT))) {
            IDMXDUtils.spawn((ServerWorld)this.getWorld(), this.getPos(), this.getXpToDrop());
        }
    }
}
