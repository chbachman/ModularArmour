package chbachman.armour.upgrade.upgradeList;

import org.lwjgl.input.Keyboard;

import chbachman.armour.upgrade.UpgradeProjectile;

public class UpgradeHook extends UpgradeProjectile{

	public UpgradeHook() {
		super("hook");
	}

	@Override
	public int getKey(){
		return Keyboard.KEY_D;
	}
	
	
	
	

}
