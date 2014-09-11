package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.IConfigurableElectric;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;

public class UpgradeEnergy extends Upgrade{

	public final int maxTransfer;
	public final int capacity;
	
	public UpgradeEnergy(int maxTransfer, int capacity) {
		super("Energy");
		this.maxTransfer = maxTransfer;
		this.capacity = capacity;
	}
	
	public void onUpgradeAddition(IModularItem armour, ItemStack stack){
		if(armour instanceof IConfigurableElectric){
			IConfigurableElectric config = (IConfigurableElectric) armour;
			config.setCapacity(stack, 1000);
			config.setCapacity(stack, 1000);
		}
	}

}
