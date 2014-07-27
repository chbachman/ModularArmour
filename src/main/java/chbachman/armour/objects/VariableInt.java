package chbachman.armour.objects;

import chbachman.armour.util.NBTHelper;
import net.minecraft.item.ItemStack;

public class VariableInt {
	
	public final int defaultData;
	public final String key;
	
	public VariableInt(String key, int defaultData){
		this.defaultData = defaultData;
		this.key = key;
	}
	
	public int get(ItemStack stack){
		
		NBTHelper.createDefaultStackTag(stack);
		
		int output = stack.stackTagCompound.getInteger(key);
		
		if(output == 0){
			return defaultData;
		}
		
		return output;
		
	}
	
	public void set(ItemStack stack, int data){	
		
		NBTHelper.createDefaultStackTag(stack);
		
		stack.stackTagCompound.setInteger(key, data);
	}

}
