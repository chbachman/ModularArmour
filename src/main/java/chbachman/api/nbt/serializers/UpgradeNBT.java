package chbachman.api.nbt.serializers;

import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.nbt.NBTSerializer;
import chbachman.api.nbt.NBTContext;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;

public class UpgradeNBT implements NBTSerializer<IUpgrade>{

	@Override
	public IUpgrade loadFromNBT(NBTTagCompound d, NBTContext context){
		IUpgrade upgrade = UpgradeRegistry.getUpgrade(d.getString("ID"));

		return upgrade;
	}

	@Override
	public void saveToNBT(IUpgrade data, NBTTagCompound d, NBTContext context){
		d.setString("ID", data.getBaseName());
	}

}
