package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.VariableInt;

public class UpgradeCamelPack extends Upgrade{

	final int maxWaterStorage = 100;
	
	VariableInt water = new VariableInt("waterLevel", 0);
	
	public UpgradeCamelPack() {
		super("camelPack");
	}
	
	@Override
	public void onUpgradeAddition(IModularItem item, ItemStack stack) {
		NBTHelper.createDefaultStackTag(stack);
		
		stack.stackTagCompound.setInteger("camelPackFill", 0);
		stack.stackTagCompound.setBoolean("isCamelPack", true);
		stack.stackTagCompound.setInteger("camelPackMax", 100);
		
	}

	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		return armorType == ArmourSlot.CHESTPLATE.id;
	}
	
	
	
}
