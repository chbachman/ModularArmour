package chbachman.api;

import net.minecraft.nbt.NBTTagCompound;
import chbachman.armour.upgrade.UpgradeList;
import chbachman.armour.util.NBTAble;

public class UpgradeNBT implements NBTAble<IUpgrade>{
	
	public static final UpgradeNBT INSTANCE = new UpgradeNBT();

	@Override
	public IUpgrade loadFromNBT(NBTTagCompound nbt){
		IUpgrade upgrade = UpgradeList.INSTANCE.get(nbt.getString("ID"));

		if(upgrade == null){
			return null;
		}

		upgrade.setDisabled(nbt.getBoolean("disabled"));

		return upgrade;
	}

	@Override
	public void saveToNBT(IUpgrade upgrade, NBTTagCompound nbt){
		nbt.setString("ID", upgrade.getBaseName());
		nbt.setBoolean("disabled", upgrade.isDisabled());
	}

}
