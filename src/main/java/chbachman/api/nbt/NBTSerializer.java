package chbachman.api.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTSerializer<E> {

    /**
     * Loads from NBTTagCompound
     * 
     * @param d
     * @param context
     * @return Data, or null if does not exist.
     */
    public E loadFromNBT(NBTTagCompound d);

    /**
     * Saves to NBTTagCompound
     * 
     * @param data
     * @param d
     */
    public void saveToNBT(E data, NBTTagCompound d);

}
