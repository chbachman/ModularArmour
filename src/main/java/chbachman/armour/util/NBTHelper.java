package chbachman.armour.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.armour.upgrade.Upgrade;

public class NBTHelper {

	public static NBTTagCompound createStackTagCompound(){

		NBTTagCompound nbt = new NBTTagCompound();

		NBTTagList nbtList = new NBTTagList();

		nbt.setTag("UpgradeList", nbtList);

		return nbt;
	}

	public static NBTTagList getNBTTagList(NBTTagCompound nbt){

		if(nbt == null){
			nbt = createStackTagCompound();
		}
		
		if(!nbt.hasKey("UpgradeList")){
			nbt.setTag("UpgradeList", new NBTTagList());
		}

		NBTTagList tagList = nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND);
		
		return tagList;

	}

	public static List<Upgrade> getUpgradeListFromNBT(NBTTagCompound nbt){

		if(nbt == null){
			nbt = createStackTagCompound();
		}
		
		NBTTagList nbtList = nbt.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND);

		if(nbtList == null){
			nbtList = new NBTTagList();
		}

		nbt.setTag("UpgradeList", nbtList);

		List<Upgrade> output = new ArrayList<Upgrade>();

		for(int i = 0; i < nbtList.tagCount(); i++){
			Upgrade upgrade = Upgrade.getUpgradeFromNBT(nbtList.getCompoundTagAt(i));
			if(upgrade != null){
				output.add(upgrade);
			}
		}

		return output;


	}

}
