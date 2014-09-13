package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.IConfigurableElectric;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;

public class UpgradeEnergy extends Upgrade{

	public final int maxTransfer;
	public final int capacity;
	
	public UpgradeEnergy(String name, int maxTransfer, int capacity) {
		super(name);
		this.maxTransfer = maxTransfer;
		this.capacity = capacity;
	}
	
	public void onUpgradeAddition(IModularItem armour, ItemStack stack){
		if(armour instanceof IConfigurableElectric){
			IConfigurableElectric config = (IConfigurableElectric) armour;
			config.setCapacity(stack, capacity);
			config.setMaxTransfer(stack, maxTransfer);
		}
	}

}
