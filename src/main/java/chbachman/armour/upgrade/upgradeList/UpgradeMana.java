package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.items.holder.ManaHolder;

public class UpgradeMana extends UpgradeBasic{
	
	public final int capacity;
	
	private IUpgrade dependency = null;
	
	public UpgradeMana(String name, int capacity) {
		super(name);
		this.capacity = capacity;
	}
	
	@Override
	public void onUpgradeAddition(IModularItem armour, ItemStack stack){
		
		ManaHolder holder = (ManaHolder) armour.getHolder();
		
		holder.maxMana.set(stack, capacity);
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType){
		return item.getHolder() instanceof ManaHolder;
	}

	@Override
	public IUpgrade[] getDependencies() {
		if(dependency == null){
			return null;
		}
		
        return new IUpgrade[]{this.dependency};
    }
	
	public UpgradeMana setDependencies(IUpgrade upgrade){
		this.dependency = upgrade;
		return this;
	}
}
