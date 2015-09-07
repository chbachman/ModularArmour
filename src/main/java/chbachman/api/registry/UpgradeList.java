package chbachman.api.registry;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ObjectMap;

public class UpgradeList extends ObjectMap<String, IUpgrade> {

    public IUpgrade get(Class<? extends Upgrade> clazz) {
        for (IUpgrade upgrade : this.values()) {
            if (upgrade.getClass() == clazz) {
                return upgrade;
            }
        }

        return null;
    }

    public IUpgrade put(IUpgrade upgrade) {
        return this.put(upgrade.getBaseName(), upgrade);
    }

}
