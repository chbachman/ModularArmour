package chbachman.armour.util;

import java.util.ArrayList;
import java.util.List;

import cofh.util.StringHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeList;

public class UpgradeUtil {
	
	public static boolean doesPlayerHaveUpgrade(EntityPlayer player, Upgrade upgrade){
		ItemStack[] armourArray = player.inventory.armorInventory;
		
		for(ItemStack armour: armourArray){
			
			if(armour != null){
				if(armour.getItem() instanceof ItemModularArmour){
					
					NBTHelper.createDefaultStackTag(armour);
					
					List<Upgrade> upgradeList = NBTHelper.getUpgradeListFromNBT(armour.stackTagCompound);
					
					for(Upgrade armourUpgrade : upgradeList){
						
						if(upgrade.getId() == armourUpgrade.getId()){
							return true;
						}
					}
				}
			}
			
		}
		
		return false;
	}
	
	public static boolean doesPlayerHaveUpgrade(EntityPlayer player, String string) {
		return doesPlayerHaveUpgrade(player, getUpgradeFromName(string));
	}

	public static Upgrade getUpgradeFromName(String unlocalizedName){
		for(Upgrade upgrade : UpgradeList.list){
			if(upgrade.getUnlocalizedName().equals(unlocalizedName)){
				return upgrade;
			}
		}
		
		for(Upgrade upgrade : UpgradeList.list){
		    if(upgrade.getUnlocalizedName().equals("upgrade.chbachman." + StringHelper.camelCase(unlocalizedName).replace(" ", "") +  ".name")){
		        return upgrade;
		    }
		}
		
		return null;
	}
	
	public static void removeUpgrade(ItemStack stack, Upgrade upgrade){
		if(stack.stackTagCompound == null){
			NBTHelper.createDefaultStackTag(stack);
			return;
		}
		
		if(stack.getItem() instanceof ItemModularArmour){

			NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
			
			for(int i = 0; i < list.tagCount(); i++){
				Upgrade upgradeList = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
				
				if(upgradeList != null && upgradeList.getId() == upgrade.getId()){
					return;
				}
			}
		}

	}
	
	public static List<String> getDependencyList(Upgrade upgrade){
		List<String> list = new ArrayList<String>();
		list.add(upgrade.getUnlocalizedName());
		return list;
	}
	
	public static List<String> getDependencyList(String upgrade){
		List<String> list = new ArrayList<String>();
		list.add(upgrade);
		return list;
	}
	
	
	public static boolean doesItemStackContainUpgrade(ItemStack stack, Upgrade upgrade){
		
		if(stack.stackTagCompound == null){
			NBTHelper.createDefaultStackTag(stack);
			return false;
		}
		
		NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
		
		for(int i = 0; i < list.tagCount(); i++){
			Upgrade up = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
			
			if(up != null && upgrade != null && up.getId() == upgrade.getId()){
				return true;
			}
		}

		return false;
	}

	public static boolean doesItemStackContainUpgrade(ItemStack stack, String name){

		return doesItemStackContainUpgrade(stack, getUpgradeFromName(name));
	}
	

}
