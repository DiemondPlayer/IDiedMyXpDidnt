package net.diemond_player.idmxd.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public interface IDMXDAccessor {
    boolean idmxd$isDeathDrop();

    void idmxd$setAsDeathDrop(boolean bl);

    void idmxd$setOrbAge(int age);

    int idmxd$getPickingCount();

    void idmxd$setPickingCount(int pickingCount);

    int idmxd$getAmount();
}
