package chbachman.armour.items.armour.logic;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.nbt.NBTStorage;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.register.Botania;
import chbachman.armour.util.ConfigHelper;
import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.Optional;

public class RFUpgradeLogic extends UpgradeLogicAdv implements IEnergyContainerItem{

	public RFUpgradeLogic(IModularItem item) {
		super(item);
	}

	private NBTStorage<Integer> capacity = new NBTStorage<Integer>("capacity", 100);
	private NBTStorage<Integer> maxTransfer = new NBTStorage<Integer>("maxTransfer", 100);
	
	public int getCapacity(ItemStack stack) {
		return this.capacity.get(stack);
	}

	public void setCapacity(ItemStack stack, int amount) {
		this.capacity.set(stack, amount);
	}

	public int getMaxTransfer(ItemStack stack) {
		return this.maxTransfer.get(stack);
	}

	public void setMaxTransfer(ItemStack stack, int amount){
		this.maxTransfer.set(stack, amount);
	}

	@Override
	public void damageArmour(ItemStack stack, int damage){
		this.extractEnergy(stack, damage, false);
	}

	@Override
	public void healArmour(ItemStack stack, int toHeal){
		this.receiveEnergy(stack, toHeal, false);
	}

	@Override
	public void addInformation(List<String> list, ItemStack stack){
		if (!StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		} else {

			list.add(StringHelper.localize("info.cofh.charge") + ": " + getEnergyStored(stack) + " / " + this.capacity.get(stack) + " RF");
		}
		
		super.addInformation(list, stack);
	}

	@Override
	public int getDisplayDamage(ItemStack stack){
		NBTHelper.createDefaultStackTag(stack);
		return 1 + this.capacity.get(stack) - getEnergyStored(stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack){
		return 1 + this.capacity.get(stack);
	}

	@Override
	public boolean isDamaged(ItemStack stack){
		return stack.getItemDamage() != Short.MAX_VALUE;
	}
	
	@Override
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack) {

		int energy = 0;

		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

			if (upgrade != null) {
				energy += upgrade.onTick(world, player, stack, ArmourSlot.getArmourSlot((this.item.getSlot())));
			}

		}

		if(energy < 0){
			this.receiveEnergy(stack, energy, false);
		}else{
			this.extractEnergy(stack, energy, false);
		}

	}

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

	// IPixieSpawner
	@Override
	@Optional.Method(modid = "Botania")
	public float getPixieChance(ItemStack stack){
		return ConfigHelper.get(ConfigHelper.SPEED, Botania.pixie, "Pixie Chance", .05F);
	}

}
