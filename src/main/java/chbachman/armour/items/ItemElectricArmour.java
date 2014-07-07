package chbachman.armour.items;

import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;
import cofh.item.ItemArmorAdv;
import cofh.util.EnergyHelper;

public class ItemElectricArmour extends ItemArmorAdv implements IEnergyContainerItem{

	protected int capacity = 100;
	protected int maxTransfer = 10;

	protected int energyPerDamage = 0;

	public ItemElectricArmour(ArmorMaterial material, int type) {
		super(material, type);
	}

	
	@Override
	public int getDisplayDamage(ItemStack stack) {

		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		return 1 + capacity - stack.stackTagCompound.getInteger("Energy");
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {

		return 1 + capacity;
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {

		return stack.getItemDamage() != Short.MAX_VALUE;
	}

	// IEnergyContainerItem 
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		int stored = container.stackTagCompound.getInteger("Energy");
		int receive = Math.min(maxReceive, Math.min(capacity - stored, maxTransfer));

		if (!simulate) {
			stored += receive;
			container.stackTagCompound.setInteger("Energy", stored);
		}
		return receive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		int stored = container.stackTagCompound.getInteger("Energy");
		int extract = Math.min(maxExtract, stored);

		if (!simulate) {
			stored -= extract;
			container.stackTagCompound.setInteger("Energy", stored);
		}
		return extract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return capacity;
	}

}
