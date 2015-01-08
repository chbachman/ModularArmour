package chbachman.armour.handler;

import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.gui.ArmourContainerWrapper;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTUpgradeList;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeHandler {
    
    public static IUpgrade getResult(ArmourContainerWrapper containerWrapper) {
    	
        return Recipe.getResult(containerWrapper);
    }
    
    public static boolean addUpgrade(ItemStack stack, IUpgrade upgrade) {
        NBTHelper.createDefaultStackTag(stack);
        
        if (stack.getItem() instanceof IModularItem) {
            
        	IModularItem armour = (IModularItem) stack.getItem();
            
            if (upgrade != null && checkContain(stack, upgrade) && upgrade.isCompatible(armour, stack, armour.getSlot()) && checkDependencies(stack, upgrade)) {
                
                upgrade.onUpgradeAddition(armour, stack);
                
                NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);
                
                list.add(upgrade);
                
                return true;
                
            }
            
        }
        return false;
    }
    
    public static boolean checkContain(ItemStack stack, IUpgrade upgrade) {
    	return !UpgradeUtil.doesItemStackContainUpgrade(stack, upgrade);
    }
    
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
