package chbachman.armour.upgrade.upgradeList;

import net.minecraft.init.Items;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;

public class UpgradeCalfShields extends Upgrade {
    
    public UpgradeCalfShields() {
        super("CalfShields");
    }
    
    @Override
    public boolean isCompatible(ArmourSlot slot) {
        return slot == ArmourSlot.LEGS;
    }
    
    @Override
    public Recipe getRecipe() {
        return new Recipe(this, "i i", "i i", "i i", 'i', Items.iron_ingot);
    }
    
}
