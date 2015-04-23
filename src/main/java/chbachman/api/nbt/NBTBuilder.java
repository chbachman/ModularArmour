package chbachman.api.nbt;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.reflect.TypeToken;

public final class NBTBuilder{

	private static final Map<TypeToken<?>, NBTSerializer<?>> list = new HashMap<TypeToken<?>, NBTSerializer<?>>();
	private static final BiMap<String, NBTSerializer<?>> stringList = HashBiMap.create();

	private static final NBTContext context = new NBTContext();
	
	private NBTBuilder(){}

	public static <T> void registerNBT(Class<T> clazz, NBTSerializer<T> nbt){
		TypeToken<T> type = TypeToken.get(clazz);
		list.put(type, nbt);
		stringList.put(type.toString(), nbt);
	}
	
	public static Object load(NBTTagCompound nbt){
		
		String type = nbt.getString("type");
		
		NBTSerializer<?> serializer = stringList.get(type);

		if(serializer != null){
			return serializer.loadFromNBT(nbt, context);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T load(NBTTagCompound nbt, Class<T> clazz){
		NBTSerializer<T> serializer = (NBTSerializer<T>) list.get(clazz);
		
		return serializer.loadFromNBT(nbt, context);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NBTTagCompound save(T object){
		TypeToken<T> t = (TypeToken<T>) TypeToken.get(object.getClass());
		NBTTagCompound nbt = new NBTTagCompound();
		NBTSerializer<T> parser = (NBTSerializer<T>) list.get(t);
		
		nbt.setString("type", t.toString());
		
		parser.saveToNBT(object, nbt, context);
		
		return nbt;
	}
	
	public static <T> T save(ItemStack stack, String name, T data){
		if(stack.stackTagCompound == null){
			return null;
		}
		
		return save(stack.stackTagCompound, name, data);
	}
	
	public static <T> T save(NBTTagCompound nbt, String name, T data){
		nbt.setTag(name, save(data));
		return data;
	}
	
	//Convience Methods
	public static Object load(ItemStack stack){
		if(stack.stackTagCompound == null){
			return null;
		}
		
		return load(stack.stackTagCompound);
	}
	
	public static <T> T load(ItemStack stack, Class<T> type){
		if(stack.stackTagCompound == null){
			return null;
		}
		
		return load(stack.stackTagCompound, type);
	}
	
	public static Object load(ItemStack stack, String name){
		if(stack.stackTagCompound == null){
			return null;
		}
		
		return load(stack.stackTagCompound, name);
	}
	
	public static Object load(NBTTagCompound nbt, String name){
		return load(nbt.getCompoundTag(name));
	}
	
	public static <T> T load(ItemStack stack, String name, Class<T> type){
		if(stack.stackTagCompound == null){
			return null;
		}
		
		return load(stack.stackTagCompound, name, type);
	}
	
	public static <T> T load(NBTTagCompound nbt, String name, Class<T> type){
		return load(nbt.getCompoundTag(name), type);
	}
	
	
	
}
