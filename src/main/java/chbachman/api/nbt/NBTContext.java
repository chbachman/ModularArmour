package chbachman.api.nbt;

import net.minecraft.nbt.NBTTagCompound;

public class NBTContext{
	
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
