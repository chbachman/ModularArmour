package chbachman.armour.util;

import net.minecraft.item.ItemStack;
import cofh.api.energy.IEnergyContainerItem;
import cofh.util.EnergyHelper;

public class EnergyUtil {
    
    public static IEnergyContainerItem getItem(ItemStack stack){
        if(EnergyHelper.isEnergyContainerItem(stack)){
            return (IEnergyContainerItem) stack.getItem();
        }
        return null;
    }
    
}
