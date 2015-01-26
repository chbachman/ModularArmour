package chbachman.armour.util;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTAble<E>{
	
	public E get(NBTTagCompound d);
	
	public void save(E data, NBTTagCompound d);
	
}
