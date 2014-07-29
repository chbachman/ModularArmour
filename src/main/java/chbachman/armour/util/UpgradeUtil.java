package chbachman.armour.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.upgrade.UpgradeList;
import cofh.util.StringHelper;

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
	
	public static void removeUpgrade(ItemStack container, Upgrade upgrade){
	    
	    ItemStack stack = container.copy();
	    
		if(stack.stackTagCompound == null){
			NBTHelper.createDefaultStackTag(stack);
			return;
		}
		
		if(stack.getItem() instanceof ItemModularArmour){

			NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
			
			for(int i = 0; i < list.tagCount(); i++){
				Upgrade upgradeList = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
				
				if(upgradeList != null && upgradeList.getId() == upgrade.getId()){
				    
					list.removeTag(i);
				}
			}
			
			try{
	            
	            for(int i = 0; i < list.tagCount(); i++){
	                UpgradeHandler.checkDependencies(stack, Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i)));
	            }
	            
	        }catch(UpgradeException e){
	            throw new UpgradeException("The %s Upgrade requires the %s Upgrade", e.cause, upgrade);
	        }
	        
	        container.stackTagCompound = stack.stackTagCompound;
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
	
	public static boolean doesNBTListContainUpgrade(NBTTagList list, Upgrade upgrade){
	    
	    for(int i = 0; i < list.tagCount(); i++){
	        Upgrade up = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
	        
	        if(up != null && upgrade != null && up.getId() == upgrade.getId()){
	            return true;
	        }
	    }
	    
	    return false;
	}
	
	public static boolean doesItemStackContainUpgrade(ItemStack stack, Upgrade upgrade){
		
		if(stack.stackTagCompound == null){
			NBTHelper.createDefaultStackTag(stack);
			return false;
		}
		
		NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
		
		return doesNBTListContainUpgrade(list, upgrade);
	}

	public static boolean doesItemStackContainUpgrade(ItemStack stack, String name){

		return doesItemStackContainUpgrade(stack, getUpgradeFromName(name));
	}
	

}
