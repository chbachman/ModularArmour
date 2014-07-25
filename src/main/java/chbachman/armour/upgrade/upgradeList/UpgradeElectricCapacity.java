package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;

public class UpgradeElectricCapacity extends Upgrade{

	public UpgradeElectricCapacity() {
		super("Electric");
	}

	@Override
	public boolean isCompatible(ArmourSlot slot) {
		return true;
	}
	
	@Override
	public Recipe getRecipe() {
		return null;
	}
	
	@Override
	public void onUpgradeAddition(ItemModularArmour armour, ItemStack stack){
		armour.capacity = 10000;
	}

}
