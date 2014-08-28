package chbachman.armour.proxy;

import chbachman.api.IUpgrade;
import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.UpgradeList;
import cofh.core.key.CoFHKey;

public abstract class CommonProxy implements IProxy {
    
    @Override
    public void registerKeyBinds() {
        
        for (IUpgrade upgrade : UpgradeList.list) {
            if (upgrade instanceof KeybindUpgrade) {
                CoFHKey.addServerKeyBind((KeybindUpgrade) upgrade);
            }
        }
        
    }
    
}
