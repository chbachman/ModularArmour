package chbachman.armour.util;

import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;

public final class ConfigHelper{
	
	private ConfigHelper(){
		
	}
	
	public static void preInit(){
		
	}
	
	public static void init(){
		
	}
	
	public static void postInit(){
		
	}
	
	public static void onClose(){
		
	}
	
	public static int getEnergyCost(IUpgrade upgrade, String description, int defaul){
		
		if(upgrade == null){
			return defaul;
		}
		
		return ModularArmour.config.get("energy values", new StringBuilder(upgrade.getName()).append(":").append(description).toString(), defaul, "");
	}
	
	public static float getEnergyCost(IUpgrade upgrade, String description, float defaul){
		
		if(upgrade == null){
			return defaul;
		}
		
		return (float) ModularArmour.config.get("energy values", new StringBuilder(upgrade.getName()).append(":").append(description).toString(), defaul, "");
	}

}
