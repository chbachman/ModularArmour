package chbachman.api.nbt.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTInteger{
	
	String key;
	int def;
	
	public NBTInteger(){}
	
	public NBTInteger(String key){
		this.key = key;
	}
	
	public NBTInteger(String key, int defaul){
		this.key = key;
		this.def = defaul;
	}
	
	public int set(ItemStack stack, int data){
		int temp = this.get(stack);
		
		stack.stackTagCompound.setInteger(key, data);
		
		return temp;
	}
	
	public int set(NBTTagCompound stack, int data){
		int temp = this.get(stack);
		
		stack.setInteger(key, data);
		
		return temp;
	}
	
	public int get(NBTTagCompound stack){
		return stack == null ? def : stack.getInteger(key);
	}
	
	public int get(ItemStack stack){
		return get(stack.stackTagCompound);
	}
	
	public void setDefault(int def){
		this.def = def;
	}
	
	public int getDefault(){
		return this.def;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey(){
		return key;
	}

}
