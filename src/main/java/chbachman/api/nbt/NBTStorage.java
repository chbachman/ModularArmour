package chbachman.api.nbt;

import net.minecraft.item.ItemStack;


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
	
	public E get(ItemStack stack){
		Object obj = NBTBuilder.load(stack, key);
		
		try{
			@SuppressWarnings("unchecked")
			E data = (E) obj;
			
			return data;
			
		}catch(ClassCastException e){ //Someone changed the data to a different type behind our backs. Naughty dev.
			return null;
		}
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
