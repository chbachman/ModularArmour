package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.util.NBTHelper;

public class UpgradeGasMask extends Upgrade{

	public UpgradeGasMask() {
		super("gasMask");
	}

	@Override
	public void onUpgradeAddition(IModularItem item, ItemStack stack) {
		NBTHelper.createDefaultStackTag(stack);
		
		stack.stackTagCompound.setInteger("gasMaskFill", 1000);
		stack.stackTagCompound.setInteger("gasMaskMax", 1000);
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		return armorType == ArmourSlot.HELMET.id;
	}

}
