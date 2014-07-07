package chbachman.armour.upgrade.upgradeList;

import net.minecraft.init.Items;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;

public class UpgradeCalfShields extends Upgrade{
	
	public UpgradeCalfShields(){
		super("Calf Shields");
		
		this.recipe = new Recipe(this, "i i", "i i", "i i", 'i', Items.iron_ingot);
		
		Recipe.addToList(recipe);
	}

	@Override
	public boolean isCompatible(ArmourSlot slot) {
		return slot == ArmourSlot.LEGS;
	}
	
	

}
