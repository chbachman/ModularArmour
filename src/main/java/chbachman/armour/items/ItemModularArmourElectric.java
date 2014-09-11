package chbachman.armour.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import chbachman.armour.objects.VariableInt;
import chbachman.armour.util.NBTHelper;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.StringHelper;

public class ItemModularArmourElectric extends ItemModularArmour implements IEnergyContainerItem{
	
	private VariableInt capacity = new VariableInt("capacity", 100);
	private VariableInt maxTransfer = new VariableInt("maxTransfer", 100);
	private VariableInt energyPerDamage = new VariableInt("energyPerDamage", 100);
	
	public ItemModularArmourElectric(ArmorMaterial material, int type) {
		super(material, type);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
		NBTHelper.createDefaultStackTag(stack);
		if (!StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		} else {

			list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.capacity.get(stack) + " RF");
		}

		super.addInformation(stack, player, list, check);
	}
	
	//IEnergyContainerItem
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		NBTHelper.createDefaultStackTag(container);
		
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(capacity.get(container) - energy, Math.min(this.maxTransfer.get(container), maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		NBTHelper.createDefaultStackTag(container);
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.maxTransfer.get(container) , maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		NBTHelper.createDefaultStackTag(container);
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return this.capacity.get(container) ;
	}

	//ItemModularArmour
	@Override
	public void damageArmour(ItemStack stack, DamageSource source, int amount) {
		this.extractEnergy(stack, amount * this.energyPerDamage.get(stack), false);
	}

	@Override
	public int getMaximumDamage(ItemStack stack) {
		return this.capacity.get(stack);
	}

	@Override
	public int getCurrentDamage(ItemStack stack) {
		return this.getEnergyStored(stack);
	}
	
	
}
