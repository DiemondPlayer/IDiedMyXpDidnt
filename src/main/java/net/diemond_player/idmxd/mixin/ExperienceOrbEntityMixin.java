package net.diemond_player.idmxd.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.diemond_player.idmxd.util.IDMXDAccessor;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.entity.ExperienceOrbEntity.roundToOrbSize;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin implements IDMXDAccessor {
    @Shadow private int orbAge;
    @Shadow private int pickingCount;
    @Shadow private int amount;

    @Unique
    boolean isDeathDrop = false;

    @Override
    public boolean idmxd$isDeathDrop() {
        return this.isDeathDrop;
    }

    @Override
    public void idmxd$setAsDeathDrop(boolean bl) {
        this.isDeathDrop = bl;
    }

    @Override
    public void idmxd$setOrbAge(int age) {
        this.orbAge = age;
    }

    @Override
    public int idmxd$getPickingCount() {
        return this.pickingCount;
    }

    @Override
    public void idmxd$setPickingCount(int pickingCount) {
        this.pickingCount = pickingCount;
    }

    @Override
    public int idmxd$getAmount() {
        return this.amount;
    }

    @Inject(at = @At(value = "TAIL"), method = "tick")
    private void idmxd$tick(CallbackInfo ci) {
        if(this.isDeathDrop){
            this.orbAge--;
        }
    }
}
