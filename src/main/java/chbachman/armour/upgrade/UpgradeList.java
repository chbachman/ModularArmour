package chbachman.armour.upgrade;

import java.util.ArrayList;
import java.util.Iterator;

import chbachman.api.IUpgrade;
import chbachman.api.Upgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;

@SuppressWarnings("serial")
public class UpgradeList extends ArrayList<IUpgrade> {
    
    public static final UpgradeList INSTANCE = new UpgradeList();
    
    public IUpgrade get(String name){
    	for (IUpgrade upgrade : this) {
            if (upgrade.getBaseName().equals(name)) {
                return upgrade;
            }
        }
        
        return null;
    }
    
    public IUpgrade get(Class<? extends Upgrade> clazz) {
        for (IUpgrade upgrade : this) {
            if (upgrade.getClass() == clazz) {
                return upgrade;
            }
        }
        
        return null;
    }
    
    @Override
    public boolean add(IUpgrade upgrade) {
        if (ModularArmour.config.get("Command Enabling", upgrade.getName(), true)) {
            return super.add(upgrade);
        }
        
        Iterator<Recipe> iterator = Recipe.craftingList.iterator();
        
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipe.getRecipeOutput() == upgrade) {
                iterator.remove();
            }
        }
        
        return true;
    }
    
}
