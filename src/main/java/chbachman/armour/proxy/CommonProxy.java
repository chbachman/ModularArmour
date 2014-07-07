package chbachman.armour.proxy;

import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.Upgrade;
import cofh.key.CoFHKey;

public abstract class CommonProxy implements IProxy{
	
	@Override
	public void registerKeyBinds() {
		
		for(Upgrade upgrade : Upgrade.upgradeList){
			if(upgrade instanceof KeybindUpgrade){
				CoFHKey.addServerKeyBind( (KeybindUpgrade) upgrade );
			}
		}
		
	}

}
