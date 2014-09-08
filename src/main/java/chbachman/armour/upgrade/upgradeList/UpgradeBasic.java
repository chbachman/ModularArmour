package chbachman.armour.upgrade.upgradeList;

import chbachman.api.Upgrade;
import chbachman.armour.reference.ArmourSlot;

public class UpgradeBasic extends Upgrade{

	private ArmourSlot slot;
	
	public UpgradeBasic(String name){
		super(name);
	}
	
	public UpgradeBasic setArmourSlot(ArmourSlot slot){
		this.slot = slot;
		return this;
	}
	
	public boolean isCompatible(ArmourSlot slot){
		if(this.slot == null){
			return true;
		}
		
		return this.slot == slot;
	}
	
}
