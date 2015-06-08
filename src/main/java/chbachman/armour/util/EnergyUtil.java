package chbachman.armour.util;

import modulararmour.cofh.lib.util.helpers.EnergyHelper;
import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;

public class EnergyUtil {
    
    public static IEnergyContainerItem getItem(ItemStack stack){
        if(EnergyHelper.isEnergyContainerItem(stack)){
            return (IEnergyContainerItem) stack.getItem();
        }
        return null;
    }
    
    public static int getEnergyStored(ItemStack stack){
    	if(EnergyHelper.isEnergyContainerItem(stack)){
            return ((IEnergyContainerItem) stack.getItem()).getEnergyStored(stack);
        }
        return 0;
    }
    
    /**
     * Is the enrgy in the stack depleted
     * @param stack
     * @return whether the stack is depeleted, or false if it does not contain any energy.
     */
    public static boolean isEmpty(ItemStack stack){
    	if(EnergyHelper.isEnergyContainerItem(stack)){
    		
    		IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();
    		
    		return item.getEnergyStored(stack) == 0;
    	}
    	
    	return false;
    }
    
    /**
     * Is the energy full
     * @param stack
     * @return whether the stack is full, or false if it is not an energy item
     */
    public static boolean isFull(ItemStack stack){
    	if(EnergyHelper.isEnergyContainerItem(stack)){

    		IEnergyContainerItem item = (IEnergyContainerItem) stack.getItem();

    		return item.getEnergyStored(stack) == item.getMaxEnergyStored(stack);
    	}
    	
    	return false;
    }
    
}
