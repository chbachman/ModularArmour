package chbachman.armour.upgrade;

import java.util.HashMap;
import java.util.Iterator;

import chbachman.api.IUpgrade;
import chbachman.api.Upgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;

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
    
    public IUpgrade put(String name, IUpgrade upgrade){
    	if (ModularArmour.config.get("Command Enabling", upgrade.getName(), true)) {
            
        	return super.put(name, upgrade);
        }
        
        Iterator<Recipe> iterator = Recipe.craftingList.iterator();
        
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipe.getRecipeOutput() == upgrade) {
                iterator.remove();
            }
        }
        
        return upgrade;
    }

	@Override
	public Iterator<IUpgrade> iterator() {
		return this.values().iterator();
	}
    
}
