package chbachman.armour.util;

import net.minecraft.world.World;

public final class MiscUtil {
	
	private MiscUtil(){}

	public static boolean isClient(World world){
		return world.isRemote;
	}
	
	public static boolean isServer(World world){
		return !world.isRemote;
	}
	
}
