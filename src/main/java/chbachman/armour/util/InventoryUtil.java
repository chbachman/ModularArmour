package chbachman.armour.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryUtil{

	public static boolean doesInventoryContainItemStack(ItemStack[] inventory, ItemStack stack){
		for (ItemStack inventoryStack : inventory){
			if (InventoryUtil.itemMatches(inventoryStack, stack, false)){
				return true;
			}
		}

		return false;
	}

	public static ItemStack getItemStackFromInventory(ItemStack[] inventory, ItemStack stack){
		for (ItemStack inventoryStack : inventory){
			if (InventoryUtil.itemMatches(inventoryStack, stack, false)){
				return inventoryStack;
			}
		}

		return null;
	}

	public static boolean doesInventoryContainItemStack(IInventory inventory, ItemStack stack){
		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack inventoryStack = inventory.getStackInSlot(i);

			if (InventoryUtil.itemMatches(inventoryStack, stack, false)){
				return true;
			}
		}

		return false;
	}
	
	public static ItemStack getItemStackFromInventory(IInventory inventory, ItemStack stack){
		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack inventoryStack = inventory.getStackInSlot(i);

			if (InventoryUtil.itemMatches(inventoryStack, stack, false)){
				return inventoryStack;
			}
		}

		return null;
	}
	
	public static boolean itemMatches(ItemStack target, ItemStack input, boolean strict)
    {
        if (input == null && target != null || input != null && target == null)
        {
            return false;
        }
        
        if(input == null && target == null){
        	return true;
        }
        
        return (target.getItem() == input.getItem() && ((target.getItemDamage() == OreDictionary.WILDCARD_VALUE && !strict) || target.getItemDamage() == input.getItemDamage()));
    }

}
