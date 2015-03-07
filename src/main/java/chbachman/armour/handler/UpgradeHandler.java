package chbachman.armour.handler;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTList;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeHandler {
    
	/**
	 * Get the result for the current wrapper.
	 * @param containerWrapper
	 * @return
	 */
    public static IUpgrade getResult(IInventory containerWrapper) {
    	
        return Recipe.recipeList.getResult(containerWrapper);
    }
    
    /**
     * Add the upgrade to the ItemStack. Calls the correct methods.
     * @param stack
     * @param upgrade
     * @return
     */
    public static boolean addUpgrade(ItemStack stack, IUpgrade upgrade) {
        NBTHelper.createDefaultStackTag(stack);
        
        if (stack.getItem() instanceof IModularItem) {
            
        	IModularItem armour = (IModularItem) stack.getItem();
            
            if (upgrade != null && checkContain(stack, upgrade) && upgrade.isCompatible(armour, stack, armour.getSlot()) && checkDependencies(stack, upgrade)) {
                
                upgrade.onUpgradeAddition(armour, stack);
                
                NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);
                
                list.add(upgrade);
                
                return true;
                
            }
            
        }
        return false;
    }
    
    /**
     * Checks if the ItemStack contains the given upgrade.
     * @param stack
     * @param upgrade
     * @return
     */
    public static boolean checkContain(ItemStack stack, IUpgrade upgrade) {
    	return !UpgradeUtil.doesItemStackContainUpgrade(stack, upgrade);
    }
    
    /**
     * Checks the dependcies for the given ItemStack and Upgrade
     * @param stack
     * @param iUpgrade
     * @return
     * @throws UpgradeException if the stack need an upgrade.
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
                
                throw new UpgradeException(String.format("This upgrade needs the %s upgrade to work", dependency.getName()), iUpgrade);
            }
        }
        
        return true;
        
    }
    
}
