package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.armour.items.armour.logic.ManaUpgradeLogic;

public class UpgradeMana extends UpgradeBasic{
	
	public final int capacity;
	
	public UpgradeMana(String name, int capacity) {
		super(name);
		this.capacity = capacity;
	}
	
	@Override
	public void onUpgradeAddition(IModularItem armour, ItemStack stack){
		
		ManaUpgradeLogic holder = (ManaUpgradeLogic) armour.getLogic();
		
		holder.maxMana.set(stack, capacity);
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType){
		return item.getLogic() instanceof ManaUpgradeLogic;
	}
}
