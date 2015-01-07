package chbachman.armour.util;

import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;

public final class ConfigHelper {
	
	private ConfigHelper(){
		
	}
	
	public static int getEnergyCost(IUpgrade upgrade, String description, int defaul){
		return ModularArmour.config.get("energy values", new StringBuilder(upgrade.getName()).append(":").append(description).toString(), defaul, "");
	}

}
