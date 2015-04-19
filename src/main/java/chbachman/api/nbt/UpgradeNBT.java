package chbachman.api.nbt;

import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;

public class UpgradeNBT implements NBTAble<IUpgrade>{
	
	public static final UpgradeNBT INSTANCE = new UpgradeNBT();

	@Override
	public IUpgrade loadFromNBT(NBTTagCompound nbt){
		IUpgrade upgrade = UpgradeRegistry.getUpgrade(nbt.getString("ID"));

		return upgrade;
	}

	@Override
	public void saveToNBT(IUpgrade upgrade, NBTTagCompound nbt){
		nbt.setString("ID", upgrade.getBaseName());
	}

}
