package chbachman.armour.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTHelper {
    
    public static ItemStack createDefaultStackTag(ItemStack stack) {
        
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = createStackTagCompound();
        }
        return stack;
    }
    
    private static NBTTagCompound createStackTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();
        
        NBTTagList nbtList = new NBTTagList();
        compound.setTag("UpgradeList", nbtList);
        
        compound.setInteger("Energy", 0);
        
        compound.setInteger("Capacity", 100);
        
        compound.setInteger("MaxTransfer", 100);
        
        return compound;
    }
    
    public static NBTUpgradeList getNBTUpgradeList(ItemStack stack) {
        
        createDefaultStackTag(stack);
        
        return getNBTUpgradeList(stack.stackTagCompound);
        
    }
    
    public static NBTUpgradeList getNBTUpgradeList(NBTTagCompound nbt) {
        
        if (nbt == null) {
            nbt = createStackTagCompound();
        }
        
        if (!nbt.hasKey("UpgradeList")) {
            nbt.setTag("UpgradeList", new NBTTagList());
        }
        
        NBTUpgradeList tagList = new NBTUpgradeList((NBTTagList) nbt.getTag("UpgradeList"));
        
        return tagList;
        
    }
}
