package chbachman.armour.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeException;

public class UpgradeUtil {
	
	public static boolean doesPlayerHaveUpgrade(EntityPlayer player, Upgrade upgrade){
		ItemStack[] armourArray = player.inventory.armorInventory;
		
		for(ItemStack armour: armourArray){
			
			if(armour != null){
				if(armour.getItem() instanceof ItemModularArmour){
					
					if(armour.stackTagCompound == null){
						armour.stackTagCompound = NBTHelper.createStackTagCompound();
					}
					
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

	public static Upgrade getUpgradeFromName(String name){
		for(Upgrade upgrade : Upgrade.upgradeList){
			if(upgrade.getName().equals(name)){
				return upgrade;
			}
		}
		
		return null;
	}
	
	public static boolean addUpgrade(ItemStack stack, Upgrade upgrade){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound =  NBTHelper.createStackTagCompound();
		}

		if(stack.getItem() instanceof ItemModularArmour){

			ItemModularArmour armour = (ItemModularArmour) stack.getItem();

			if(upgrade != null && !doesItemStackContainUpgrade(stack, upgrade) && upgrade.isCompatible(armour.armorType) && checkDependencies(stack, upgrade)){
				
				upgrade.onUpgradeAddition(armour, stack);
				
				NBTHelper.getNBTTagList(stack.stackTagCompound).appendTag(upgrade.getNBT());

				return true;

			}
			
		}
		return false;
	}
	
	public static void removeUpgrade(ItemStack stack, Upgrade upgrade){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound =  NBTHelper.createStackTagCompound();
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
	
	public static boolean checkDependencies(ItemStack stack, Upgrade upgrade){
		
		if(upgrade == null){
			return false;
		}
		
		if(upgrade.getDependencies() == null || upgrade.getDependencies().isEmpty()){
			return true;
		}
		
		List<String> dependencies = upgrade.getDependencies();
		
		for(String dependency: dependencies){
			if(!doesItemStackContainUpgrade(stack, dependency)){
				throw new UpgradeException(String.format("This upgrade needs the %s upgrade to work", dependency));
			}
		}
		
		return true;
		
	}
	
	public static List<String> getDependencyList(Upgrade upgrade){
		List<String> list = new ArrayList<String>();
		list.add(upgrade.getName());
		return list;
	}
	
	public static List<String> getDependencyList(String upgrade){
		List<String> list = new ArrayList<String>();
		list.add(upgrade);
		return list;
	}
	
	
	public static boolean doesItemStackContainUpgrade(ItemStack stack, Upgrade upgrade){
		
		if(stack.stackTagCompound == null){
			stack.stackTagCompound = NBTHelper.createStackTagCompound();
			return false;
		}
		
		NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
		
		for(int i = 0; i < list.tagCount(); i++){
			Upgrade up = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
			
			if(up.getId() == upgrade.getId()){
				return true;
			}
		}

		return false;
	}

	public static boolean doesItemStackContainUpgrade(ItemStack stack, String name){

		return doesItemStackContainUpgrade(stack, getUpgradeFromName(name));
	}
	

}
