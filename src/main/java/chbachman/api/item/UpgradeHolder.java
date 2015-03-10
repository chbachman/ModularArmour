package chbachman.api.item;

import java.util.List;

import cofh.lib.util.helpers.StringHelper;
import net.minecraft.item.ItemStack;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.upgrade.IUpgrade;

public class UpgradeHolder{
	
	private final IModularItem item;
	
	public UpgradeHolder(IModularItem item){
		this.item = item;
	}
	
	/**
	 * Adds the energy and upgrade lines to the tooltip.
	 * @param list - The tooltip list
	 * @param armour - The ItemStack of the armour;
	 */
	public void addInformation(List<String> list, ItemStack stack){
		NBTHelper.createDefaultStackTag(stack);
		
		
		if (!StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		} else {

			list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.capacity.get(stack) + " RF");
		}
		
		if (!StringHelper.isControlKeyDown() && NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
			list.add(StringHelper.LIGHT_GRAY + StringHelper.localize("info.cofh.hold") + " " + StringHelper.YELLOW + StringHelper.ITALIC + StringHelper.localize("info.chbachman.control") + " " + StringHelper.END + StringHelper.LIGHT_GRAY + StringHelper.localize("info.chbachman.upgradeList") + StringHelper.END);
		} else if (NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
			for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

				list.add(upgrade.getName());

			}
		}
	}

}
