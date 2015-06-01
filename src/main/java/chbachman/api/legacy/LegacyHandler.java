package chbachman.api.legacy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import chbachman.api.nbt.NBTList;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;

public class LegacyHandler{
	
	public static void convert(ItemStack stack){
		if(stack.stackTagCompound == null){
			return;
		}
		
		convert(stack.stackTagCompound);
	}
	
	public static void convert(NBTTagCompound old){
		
		NBTList<IUpgrade> list = new NBTList<IUpgrade>();
		
		NBTTagList nbt = old.getTagList("UpgradeList", Constants.NBT.TAG_COMPOUND);
		
		for(int i = 0; i < nbt.tagCount(); i++){
			NBTTagCompound upgradeTag = nbt.getCompoundTagAt(i);
			
			IUpgrade upgrade = UpgradeRegistry.getUpgrade(upgradeTag.getString("ID"));
			
			list.add(upgrade);
		}
		
		
		
	}

}
