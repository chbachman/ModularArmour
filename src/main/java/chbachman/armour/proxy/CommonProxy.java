package chbachman.armour.proxy;

import chbachman.armour.upgrade.KeybindUpgrade;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;
import cofh.core.key.CoFHKey;

public abstract class CommonProxy implements IProxy {
    
    @Override
    public void registerKeyBinds() {
        
        for (Upgrade upgrade : UpgradeList.list) {
            if (upgrade instanceof KeybindUpgrade) {
                CoFHKey.addServerKeyBind((KeybindUpgrade) upgrade);
            }
        }
        
    }
    
}
