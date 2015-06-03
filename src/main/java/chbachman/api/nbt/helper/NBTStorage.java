package chbachman.api.nbt.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.nbt.NBTSerializer;


/**
 * Used to store one item of any NBTSerializer defined type.
 * @author Chbachman
 *
 * @param <E>
 */
public class NBTStorage<E>{
	
	String key;
	E def;
	final NBTSerializer<E> type;
	
	public NBTStorage(NBTSerializer<E> type){
		this.type = type;
	}
	
	public NBTStorage(String key, NBTSerializer<E> type){
		this.key = key;
		this.type = type;
	}
	
	public NBTStorage(String key, E defaul, NBTSerializer<E> type){
		this.key = key;
		this.def = defaul;
		this.type = type;
	}
	
	public E set(ItemStack stack, E data){
		E temp = this.get(stack);
		
		type.saveToNBT(data, stack.stackTagCompound);
		
		return temp;
	}
	
	public E set(NBTTagCompound stack, E data){
		E temp = this.get(stack);
		
		type.saveToNBT(data, stack);
		
		return temp;
	}
	
	public E get(NBTTagCompound stack){
		return type.loadFromNBT(stack);
	}
	
	public E get(ItemStack stack){
		return get(stack.stackTagCompound);
	}
	
	public void setDefault(E def){
		this.def = def;
	}
	
	public E getDefault(){
		return this.def;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public String getKey(){
		return key;
	}

}
