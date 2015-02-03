package chbachman.armour.util;

import static chbachman.armour.ModularArmour.config;
import chbachman.api.IUpgrade;

public final class ConfigHelper{

	private ConfigHelper(){}

	public static final String ENERGY = "energy values";
	public static final String SPEED = "speed values";

	
	
	public static int get(String category, IUpgrade upgrade, String description, int def){
		if(upgrade == null){
			return def;
		}
		
		return config.get(category, new StringBuilder(upgrade.getName()).append(":").append(description).toString(), def);
	}

	public static double get(String category, IUpgrade upgrade, String description, double def){
		if(upgrade == null){
			return def;
		}
		
		return config.get(category, new StringBuilder(upgrade.getName()).append(":").append(description).toString(), def);
		
	}

	public static float get(String category, IUpgrade upgrade, String description, float def){
		return (float) get(category, upgrade, description, def);
	}

}
