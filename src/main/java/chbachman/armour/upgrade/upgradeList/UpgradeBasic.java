package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;

public class UpgradeBasic extends Upgrade{

	private ArmourSlot slot = null;
	private IUpgrade dependency = null;
	
	public UpgradeBasic(String name){
		super(name);
	}
	
	public UpgradeBasic setArmourSlot(ArmourSlot slot){
		this.slot = slot;
		return this;
	}
	
	public UpgradeBasic setDependencies(IUpgrade upgrade){
		this.dependency = upgrade;
		return this;
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		if(this.slot == null){
			return true;
		}
		
        return armorType == slot.id;
    }
	
	@Override
	public IUpgrade[] getDependencies() {
		if(dependency == null){
			return null;
		}
		
        return new IUpgrade[]{this.dependency};
    }
	
	
	
}
