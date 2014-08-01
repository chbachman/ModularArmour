package chbachman.armour.upgrade;

import java.util.ArrayList;
import java.util.Iterator;

import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.upgradeList.*;

@SuppressWarnings("serial")
public class UpgradeList extends ArrayList<Upgrade> {
    
    public static final UpgradeList list = new UpgradeList();
    
    public static void preInit() {
        list.add(new UpgradeHoverJetpack());
        list.add(new UpgradeCalfShields());
        list.add(new UpgradeBasePotion());
        list.add(new UpgradeFallDamage());
        
        UpgradeElectricCapacity.ElectricUpgrades.init();
        UpgradePotion.PotionUpgrades.init();
        UpgradeDefensive.DefensiveUpgrades.init();
        
        list.add(new UpgradeSolar());
        list.add(new UpgradeAutoFeeder());
        list.add(new UpgradeSpeed());
        list.add(new UpgradeStepAssist());
        
    }
    
    public Upgrade getByClass(Class<? extends Upgrade> clazz) {
        for (Upgrade upgrade : this) {
            if (upgrade.getClass().getName().equals(clazz.getName())) {
                return upgrade;
            }
        }
        
        return null;
    }
    
    @Override
    public boolean add(Upgrade upgrade) {
        if (ModularArmour.config.get("Command Enabling", upgrade.getUnlocalizedName(), true)) {
            return super.add(upgrade);
        }
        
        Iterator<Recipe> iterator = Recipe.craftinglist.iterator();
        
        while (iterator.hasNext()) {
            Recipe recipe = iterator.next();
            if (recipe.getResult() == upgrade) {
                iterator.remove();
            }
        }
        
        return super.add(null);
    }
    
}
