package chbachman.api.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import chbachman.api.nbt.serializers.PrimitiveNBT;
import chbachman.api.nbt.serializers.UpgradeNBT;
import chbachman.api.upgrade.IUpgrade;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.reflect.TypeToken;

public final class NBTBuilder{

	static final TypeMap<NBTSerializer> list = new TypeMap<NBTSerializer>();
	static final BiMap<String, NBTSerializer> stringList = HashBiMap.create();

	static final NBTContext context = new NBTContext();
	
	static{ //Register Default Serializers
		PrimitiveNBT.register();
		registerNBT(IUpgrade.class, new UpgradeNBT());
	}
	
	private NBTBuilder(){}
	
	public static <T> void registerNBT(Class<T> clazz, NBTSerializer<T> nbt){
		TypeToken<T> type = TypeToken.get(clazz);
		list.put(type, nbt);
		stringList.put(type.toString(), nbt);
	}
	
	public static Object load(NBTTagCompound nbt){
		
		if(nbt == null){
			return null;
		}
		
		String type = nbt.getString("type");
		
		NBTSerializer<?> serializer = stringList.get(type);

		if(serializer != null){
			return serializer.loadFromNBT(nbt, context);
		}else{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T load(NBTTagCompound nbt, Class<T> clazz){
		
		if(nbt == null){
			return null;
		}
		
		if(!list.containsKey(TypeToken.get(clazz))){
			throw new SerializationException(String.format("The type %s is not supported by the NBTSerialization. Please register a serializer for it.", clazz));
		}
		
		NBTSerializer<T> serializer = (NBTSerializer<T>) list.get(TypeToken.get(clazz));
		
		return serializer.loadFromNBT(nbt, context);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NBTTagCompound save(T object){
		TypeToken<T> t = (TypeToken<T>) TypeToken.get(object.getClass());
		NBTTagCompound nbt = new NBTTagCompound();
		
		if(!list.containsKey(t)){
			throw new SerializationException(String.format("The type %s is not supported by the NBTSerialization. Please register a serializer for it.", t));
		}
		
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
		
		if(nbt == null){
			return null;
		}
		
		if(nbt.hasKey(name, Constants.NBT.TAG_COMPOUND)){
			return load(nbt.getCompoundTag(name));
		}else{
			nbt.setTag(name, new NBTTagCompound());
			return null;
		}
		
		
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
