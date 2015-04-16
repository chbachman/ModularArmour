package chbachman.api.registry;

import java.util.HashMap;
import java.util.Iterator;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Upgrade;
import chbachman.armour.ModularArmour;

@SuppressWarnings("serial")
public class UpgradeList extends HashMap<String, IUpgrade> implements Iterable<IUpgrade>{
    
    public static final UpgradeList INSTANCE = new UpgradeList();
    
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
    
    @Override
    public IUpgrade put(String name, IUpgrade upgrade){
    	if (ModularArmour.config.get("Command Enabling", upgrade.getName(), true)) {
    		FieldList.register(upgrade);
        	return super.put(name, upgrade);
        }
        
        return upgrade;
    }

	@Override
	public Iterator<IUpgrade> iterator() {
		return this.values().iterator();
	}
    
}
