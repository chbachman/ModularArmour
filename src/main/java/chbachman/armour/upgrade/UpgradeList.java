package chbachman.armour.upgrade;

import java.util.ArrayList;
import java.util.Iterator;

import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.upgradeList.*;
import chbachman.armour.upgrade.upgradeList.UpgradeElectricCapacity.ElectricUpgrades;
import chbachman.armour.upgrade.upgradeList.UpgradePotion.PotionUpgrades;

@SuppressWarnings("serial")
public class UpgradeList extends ArrayList<Upgrade>{
    
    public static final UpgradeList list = new UpgradeList();
    
    public static void preInit(){
        list.add(new UpgradeHoverJetpack());
        list.add(new UpgradeCalfShields());
        list.add(new UpgradeBasePotion());
        
        ElectricUpgrades.init();
        PotionUpgrades.init();
        
    }
    
    public Upgrade getByClass(Class<? extends Upgrade> clazz){
        for(Upgrade upgrade : this){
            if(upgrade.getClass().getName().equals(clazz.getName())){
                return upgrade;
            }
        }
        
        return null;
    }
    
    @Override
    public boolean add(Upgrade upgrade){
        if(ModularArmour.config.get("Command Enabling", upgrade.getUnlocalizedName(), true)){
            return super.add(upgrade);
        }
        
        Iterator<Recipe> iterator = Recipe.craftinglist.iterator();
        
        while(iterator.hasNext()){
            Recipe recipe = iterator.next();
            if(recipe.getResult() == upgrade){
                iterator.remove();
            }
        }
        
        return super.add(null);
    }
    
    
    
    
    
}
