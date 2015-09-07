package chbachman.armour.upgrade.upgradeList;

import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;

public class UpgradeCamelPack extends Upgrade {

    final int maxWaterStorage = 100;

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
