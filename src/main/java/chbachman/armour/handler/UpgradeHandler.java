package chbachman.armour.handler;

import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTUpgradeList;
import chbachman.armour.util.UpgradeUtil;

public class UpgradeHandler {
    
    public static IUpgrade getResult(IInventory containerWrapper) {
        return Recipe.getResult(containerWrapper);
    }
    
    public static boolean addUpgrade(ItemStack stack, IUpgrade upgrade) {
        NBTHelper.createDefaultStackTag(stack);
        
        if (stack.getItem() instanceof IModularItem) {
            
        	IModularItem armour = (IModularItem) stack.getItem();
            
            if (upgrade != null && checkContain(stack, upgrade) && upgrade.isCompatible(armour.getSlot()) && checkDependencies(stack, upgrade)) {
                
                upgrade.onUpgradeAddition(armour, stack);
                
                NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);
                
                if (upgrade.isRepeatable()) {
                    for (IUpgrade listUpgrade : list) {
                        
                        if (listUpgrade.getId() == upgrade.getId()) {
                            
                            //TODO: Fix this
                            //compound.setInteger("amount", compound.getInteger("amount") + 1);
                            
                            return true;
                        }
                    }
                }
                
                list.add(upgrade);
                
                return true;
                
            }
            
        }
        return false;
    }
    
    public static boolean checkContain(ItemStack stack, IUpgrade upgrade) {
        if (upgrade.isRepeatable()) {
            return true;
        } else {
            return !UpgradeUtil.doesItemStackContainUpgrade(stack, upgrade);
        }
    }
    
    public static boolean checkDependencies(ItemStack stack, IUpgrade iUpgrade) {
        
        if (iUpgrade == null) {
            return false;
        }
        
        if (iUpgrade.getDependencies() == null || iUpgrade.getDependencies().isEmpty()) {
            return true;
        }
        
        List<String> dependencies = iUpgrade.getDependencies();
        
        for (String dependency : dependencies) {
            if (!UpgradeUtil.doesItemStackContainUpgrade(stack, dependency)) {
                
                IUpgrade up = UpgradeUtil.getUpgradeFromName(dependency);
                
                throw new UpgradeException(String.format("This upgrade needs the %s upgrade to work", up.getName()), iUpgrade);
            }
        }
        
        return true;
        
    }
    
}
