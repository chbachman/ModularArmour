package chbachman.api.util;

import chbachman.api.nbt.NBTHelper;
import net.minecraft.item.ItemStack;

public class VariableInt{
    
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
    
    public void increment(ItemStack stack, int amount){
    	
    	this.set(stack, this.get(stack) + amount);
    	
    }
    
    public String toString(){
        return key;
    }
    
}
