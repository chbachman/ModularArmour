package chbachman.api.nbt.serializers;

import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.configurability.Percentage;
import chbachman.api.nbt.NBTSerializer;

public class PercentageNBT implements NBTSerializer<Percentage>{

	@Override
	public Percentage loadFromNBT(NBTTagCompound d){
		return new Percentage(d.getByte("Data"));
	}

	@Override
	public void saveToNBT(Percentage data, NBTTagCompound d){
		d.setByte("Data", (byte) data.getAmount());
	}

}
