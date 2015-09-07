package chbachman.armour.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public final class MiscUtil {

    private MiscUtil() {
    }

    public static boolean isServer(Entity entity) {
        return !entity.worldObj.isRemote;
    }

    public static boolean isClient(Entity entity) {
        return entity.worldObj.isRemote;
    }

    public static boolean isClient(World world) {
        return world.isRemote;
    }

    public static boolean isServer(World world) {
        return !world.isRemote;
    }

}
