package chbachman.api.nbt;

import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.UpgradeList;

public class UpgradeNBT implements NBTAble<IUpgrade>{
	
	public static final UpgradeNBT INSTANCE = new UpgradeNBT();

	@Override
	public IUpgrade loadFromNBT(NBTTagCompound nbt){
		IUpgrade upgrade = UpgradeList.INSTANCE.get(nbt.getString("ID"));

		if(upgrade == null){
			return null;
		}

		return upgrade;
	}

	@Override
	public void saveToNBT(IUpgrade upgrade, NBTTagCompound nbt){
		nbt.setString("ID", upgrade.getBaseName());
	}

}
