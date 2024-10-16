package net.diemond_player.idmxd.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.diemond_player.idmxd.util.IDMXDAccessor;
import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    @ModifyReturnValue(at = @At(value = "TAIL"), method = "isMergeable(Lnet/minecraft/entity/ExperienceOrbEntity;II)Z")
    private static boolean idmxd$merge(boolean original, @Local(argsOnly = true) ExperienceOrbEntity orb) {
        if(original && ((IDMXDAccessor)orb).idmxd$isDeathDrop()){
            return false;
        }else{
            return original;
        }
    }
}
