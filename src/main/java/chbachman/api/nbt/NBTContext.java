package chbachman.api.nbt;

import net.minecraft.nbt.NBTTagCompound;

import com.google.gson.reflect.TypeToken;

public class NBTContext{
	
	public boolean isSerializable(Class<?> type){
		return NBTBuilder.list.containsKey(TypeToken.get(type));
	}
	
	public Object loadFromNBT(NBTTagCompound nbt){
		return NBTBuilder.load(nbt);
	}
	
	public <T> T loadFromNBT(NBTTagCompound nbt, Class<T> type){
		return NBTBuilder.load(nbt, type);
	}
	
	public NBTTagCompound saveToNBT(Object data){
		return NBTBuilder.save(data);
	}

}
