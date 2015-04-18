package chbachman.armour.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryUtil{

	public static boolean doesInventoryContainItemStack(ItemStack[] inventory, ItemStack stack){
		for (ItemStack inventoryStack : inventory){
			if (OreDictionary.itemMatches(inventoryStack, stack, false)){
				return true;
			}
		}

		return false;
	}

	public static ItemStack getItemStackFromInventory(ItemStack[] inventory, ItemStack stack){
		for (ItemStack inventoryStack : inventory){
			if (OreDictionary.itemMatches(inventoryStack, stack, false)){
				return inventoryStack;
			}
		}

		return null;
	}

	public static boolean doesInventoryContainItemStack(IInventory inventory, ItemStack stack){
		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack inventoryStack = inventory.getStackInSlot(i);

			if (OreDictionary.itemMatches(inventoryStack, stack, false)){
				return true;
			}
		}

		return false;
	}
	
	public static ItemStack getItemStackFromInventory(IInventory inventory, ItemStack stack){
		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack inventoryStack = inventory.getStackInSlot(i);

			if (OreDictionary.itemMatches(inventoryStack, stack, false)){
				return inventoryStack;
			}
		}

		return null;
	}

}
