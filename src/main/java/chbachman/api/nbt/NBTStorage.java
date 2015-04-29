package chbachman.api.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


/**
 * Used to store one item of any NBTSerializer defined type.
 * @author Chbachman
 *
 * @param <E>
 */
public class NBTStorage<E>{
	
	String key;
	E def;
	
	public NBTStorage(){}
	
	public NBTStorage(String key){
		this(key, null);
	}
	
	public NBTStorage(E def){
		this(null, def);
	}
	
	public NBTStorage(String key, E defaul){
		this.key = key;
		this.def = defaul;
	}
	
	public E set(ItemStack stack, E data){
		E temp = this.get(stack);
		
		NBTBuilder.save(stack, key, data);
		
		return temp;
	}
	
	public E set(NBTTagCompound stack, E data){
		E temp = this.get(stack);
		
		NBTBuilder.save(stack, key, data);
		
		return temp;
	}
	
	public E get(NBTTagCompound stack){
		Object obj = NBTBuilder.load(stack, key);
		
		try{
			@SuppressWarnings("unchecked")
			E data = (E) obj;
			
			return data == null ? def : data;
			
		}catch(ClassCastException e){ //Someone changed the data to a different type behind our backs. Naughty dev.
			return def;
		}
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
