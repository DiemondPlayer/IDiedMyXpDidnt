package net.diemond_player.idmxd.util;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static net.minecraft.entity.ExperienceOrbEntity.roundToOrbSize;

public class IDMXDUtils{
    public static void spawn(ServerWorld world, Vec3d pos, int amount) {
        while (amount > 0) {
            int i = roundToOrbSize(amount);
            amount -= i;
            if (!wasMergedIntoExistingOrb(world, pos, i)) {
                ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), i);
                ((IDMXDAccessor)experienceOrbEntity).idmxd$setAsDeathDrop(true);
                world.spawnEntity(experienceOrbEntity);
            }
        }
    }
    private static boolean wasMergedIntoExistingOrb(ServerWorld world, Vec3d pos, int amount) {
        Box box = Box.of(pos, 1.0, 1.0, 1.0);
        int i = world.getRandom().nextInt(40);
        List<ExperienceOrbEntity> list = world.getEntitiesByType(TypeFilter.instanceOf(ExperienceOrbEntity.class), box, orb -> isMergeable(orb, i, amount));
        if (!list.isEmpty()) {
            ExperienceOrbEntity experienceOrbEntity = (ExperienceOrbEntity)list.get(0);
            ((IDMXDAccessor)experienceOrbEntity).idmxd$setPickingCount(((IDMXDAccessor)experienceOrbEntity).idmxd$getPickingCount() + 1);
            ((IDMXDAccessor)experienceOrbEntity).idmxd$setOrbAge(0);
            return true;
        } else {
            return false;
        }
    }
    private static boolean isMergeable(ExperienceOrbEntity orb, int seed, int amount) {
        return !orb.isRemoved() && (orb.getId() - seed) % 40 == 0 && ((IDMXDAccessor)orb).idmxd$getAmount() == amount;
    }
    public static int countXp(int experienceLevel, float experienceProgress) {
        int summ = 0;
        for(int i = experienceLevel - 1; i>=0; i--){
            summ += getLevelExperience(i);
        }
        summ += MathHelper.floor(experienceProgress * (float)getLevelExperience(experienceLevel));
        return summ;
    }
    public static int getLevelExperience(int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        } else {
            return experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
        }
    }
}
