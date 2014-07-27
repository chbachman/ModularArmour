package chbachman.armour.proxy;

import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;
import cofh.key.CoFHKey;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerKeyBinds() {
		super.registerKeyBinds();
		for(Upgrade normalUpgrade : UpgradeList.list){
			if(normalUpgrade instanceof KeybindUpgrade){
				
				KeybindUpgrade upgrade = (KeybindUpgrade) normalUpgrade; 
				
				CoFHKey.addKeyBind(upgrade);
				//ClientRegistry.registerKeyBinding(new KeyBinding(upgrade.getKeyName(), upgrade.getKey(), "Modular Armour"));
			}
		}
	}
	
	

}
