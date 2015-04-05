package chbachman.armour.items.armour;

import net.minecraft.item.ItemStack;
import chbachman.armour.items.armour.logic.RFUpgradeLogic;
import cofh.api.energy.IEnergyContainerItem;

public class RFModularArmour extends ItemModularArmour implements IEnergyContainerItem{

	public RFModularArmour(ArmorMaterial material, int type) {
		super(material, type);
		this.holder = new RFUpgradeLogic(this);
	}

	public RFUpgradeLogic getHolder(){
		return (RFUpgradeLogic) this.holder;
	}
	
	public int getCapacity(ItemStack stack) {
		return this.getHolder().getCapacity(stack);
	}

	public void setCapacity(ItemStack stack, int amount) {
		this.getHolder().setCapacity(stack, amount);
	}

	public int getMaxTransfer(ItemStack stack) {
		return this.getHolder().getMaxTransfer(stack);
	}

	public void setMaxTransfer(ItemStack stack, int amount) {
		this.getHolder().setMaxTransfer(stack, amount);
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
