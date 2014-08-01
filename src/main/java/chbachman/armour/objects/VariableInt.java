package chbachman.armour.objects;

import net.minecraft.item.ItemStack;
import chbachman.armour.util.NBTHelper;

public class VariableInt {
    
    public final int defaultData;
    public final String key;
    
    public VariableInt(String key, int defaultData) {
        this.defaultData = defaultData;
        this.key = key;
    }
    
    public int get(ItemStack stack) {
        
        NBTHelper.createDefaultStackTag(stack);
        
        int output = stack.stackTagCompound.getInteger(this.key);
        
        if (output == 0) {
            return this.defaultData;
        }
        
        return output;
        
    }
    
    public void set(ItemStack stack, int data) {
        
        NBTHelper.createDefaultStackTag(stack);
        
        stack.stackTagCompound.setInteger(this.key, data);
    }
    
    public String toString(){
        return key;
    }
    
}
