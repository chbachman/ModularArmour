package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.api.Upgrade;

public class UpgradeEnergy extends Upgrade{

	public final int maxTransfer;
	public final int capacity;
	
	private IUpgrade dependency = null;
	
	public UpgradeEnergy(String name, int maxTransfer, int capacity) {
		super(name);
		this.maxTransfer = maxTransfer;
		this.capacity = capacity;
	}
	
	public void onUpgradeAddition(IModularItem armour, ItemStack stack){
		armour.setCapacity(stack, capacity);
		armour.setMaxTransfer(stack, maxTransfer);
	}
	
	public IUpgrade[] getDependencies() {
		if(dependency == null){
			return null;
		}
		
        return new IUpgrade[]{this.dependency};
    }
	
	public UpgradeEnergy setDependencies(IUpgrade upgrade){
		this.dependency = upgrade;
		return this;
	}

}
