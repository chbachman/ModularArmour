package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
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
