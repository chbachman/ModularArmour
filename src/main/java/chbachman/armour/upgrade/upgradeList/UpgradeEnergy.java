package chbachman.armour.upgrade.upgradeList;

import java.util.List;

import net.minecraft.item.ItemStack;
import chbachman.api.IConfigurableElectric;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.api.Upgrade;
import chbachman.armour.util.UpgradeUtil;

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
		if(armour instanceof IConfigurableElectric){
			IConfigurableElectric config = (IConfigurableElectric) armour;
			config.setCapacity(stack, capacity);
			config.setMaxTransfer(stack, maxTransfer);
		}
	}
	
	public List<IUpgrade> getDependencies() {
		if(dependency == null){
			return null;
		}
		
        return UpgradeUtil.getDependencyList(dependency);
    }
	
	public UpgradeEnergy setDependencies(IUpgrade upgrade){
		this.dependency = upgrade;
		return this;
	}

}
