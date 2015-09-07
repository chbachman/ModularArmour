package chbachman.armour.handler;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.nbt.helper.NBTList;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.ImmutableArray;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeHandler {

    /**
     * Get the result for the current wrapper.
     * 
     * @param containerWrapper
     * @return
     */
    public static IUpgrade getResult(IInventory crafting) {

        ImmutableArray array = UpgradeRegistry.getRecipeList();

        for (Recipe recipe : UpgradeRegistry.getRecipeList()) {
            if (recipe.matches(crafting)) {
                return recipe.getCraftingResult();
            }
        }

        return null;
    }

    /**
     * Add the upgrade to the ItemStack. Calls the correct methods. Catches the
     * UpgradeException and returns false if caught.
     * 
     * @param stack
     * @param upgrade
     * @return
     */
    public static boolean addUpgradeChecked(ItemStack stack, IUpgrade upgrade) {
        try {
            return addUpgrade(stack, upgrade);
        } catch (UpgradeException e) {
            return false;
        }
    }

    /**
     * Add the upgrade to the ItemStack. Calls the correct methods. Can throw a
     * Upgrade Exception.
     * 
     * @param stack
     * @param upgrade
     * @return
     */
    public static boolean addUpgrade(ItemStack stack, IUpgrade upgrade) {
        NBTHelper.createDefaultStackTag(stack);

        if (stack.getItem() instanceof IModularItem) {

            IModularItem armour = (IModularItem) stack.getItem();

            if (upgrade != null && checkContain(stack, upgrade) && upgrade.isCompatible(armour, stack, armour.getSlot()) && checkDependencies(stack, upgrade)) {

                addUpgradeInternal(stack, upgrade);
                return true;
            }

        }
        return false;
    }

    /**
     * Adds the upgrade to the given stack, with no checks.
     * 
     * @return
     */
    public static void addUpgradeInternal(ItemStack stack, IUpgrade upgrade) {
        if (upgrade != null) {

            upgrade.onUpgradeAddition((IModularItem) stack.getItem(), stack);

            NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(stack);

            list.add(upgrade);

        }
    }

    /**
     * Checks if the ItemStack contains the given upgrade.
     * 
     * @param stack
     * @param upgrade
     * @return
     */
    public static boolean checkContain(ItemStack stack, IUpgrade upgrade) {
        return !UpgradeUtil.doesItemStackContainUpgrade(stack, upgrade);
    }

    /**
     * Checks the dependcies for the given ItemStack and Upgrade
     * 
     * @param stack
     * @param iUpgrade
     * @return
     * @throws UpgradeException
     *             if the stack need an upgrade.
     */
    public static boolean checkDependencies(ItemStack stack, IUpgrade iUpgrade) {

        if (iUpgrade == null) {
            return false;
        }

        if (iUpgrade.getDependencies() == null || iUpgrade.getDependencies().length == 0) {
            return true;
        }

        IUpgrade[] dependencies = iUpgrade.getDependencies();

        for (IUpgrade dependency : dependencies) {
            if (!UpgradeUtil.doesItemStackContainUpgrade(stack, dependency)) {

                throw new UpgradeException(String.format("This upgrade needs the %s upgrade to work", StatCollector.translateToLocal(dependency.getName())), iUpgrade);
            }
        }

        return true;

    }

}
