package chbachman.armour.items.bauble;

import net.minecraft.item.ItemStack;
import chbachman.armour.items.armour.logic.RFUpgradeLogic;
import cofh.api.energy.IEnergyContainerItem;

public class RFBauble extends ItemBauble implements IEnergyContainerItem{

	public RFBauble() {
		this.holder = new RFUpgradeLogic(this);
	}

	public RFUpgradeLogic getHolder(){
		return (RFUpgradeLogic) this.holder;
	}
	
	//IEnergyContainerItem
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return this.getHolder().receiveEnergy(container, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return this.getHolder().extractEnergy(container, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return this.getHolder().getEnergyStored(container);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return this.getHolder().getMaxEnergyStored(container);
	}

}
