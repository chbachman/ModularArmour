package chbachman.armour.util;

import net.minecraft.world.World;

public class WorldUtil {
	
	public static boolean isServer(World world){
		return !world.isRemote;
	}
	
	public static boolean isClient(World world){
		return world.isRemote;
	}

}
