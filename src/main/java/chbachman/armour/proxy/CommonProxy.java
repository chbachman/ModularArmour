package chbachman.armour.proxy;

import chbachman.api.registry.UpgradeList;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.upgrade.KeybindUpgrade;
import cofh.core.key.CoFHKey;

public abstract class CommonProxy implements IProxy {
    
    @Override
    public void registerKeyBinds() {
        
        for (IUpgrade upgrade : UpgradeList.INSTANCE) {
            if (upgrade instanceof KeybindUpgrade) {
                CoFHKey.addServerKeyBind((KeybindUpgrade) upgrade);
            }
        }
        
    }
    
}
