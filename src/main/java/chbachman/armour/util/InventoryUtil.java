package chbachman.armour.util;

import net.minecraft.item.ItemStack;

public class InventoryUtil{

	public static boolean doesInventoryContainItemStack(ItemStack[] inventory, ItemStack stack){
		for (ItemStack inventoryStack : inventory){
			if (areItemStacksEqual(inventoryStack, stack)){
				return true;
			}
		}

		return false;
	}
	
	public static ItemStack getItemStackFromInventory(ItemStack[] inventory, ItemStack stack){
		for (ItemStack inventoryStack : inventory){
			if (areItemStacksEqual(inventoryStack, stack)){
				return inventoryStack;
			}
		}

		return null;
	}

	/**
	 * Does the null checks and calls the internal.
	 * @param one
	 * @param two
	 * @return
	 */
	public static boolean areItemStacksEqual(ItemStack one, ItemStack two){
		return one == null && two == null ? true : (one != null && two != null ? areItemStacksEqualInternal(one, two) : false);
	}

	/**
	 * Does the actual checks. Currently checks: Item.
	 * @param one
	 * @param two
	 * @return
	 */
	private static boolean areItemStacksEqualInternal(ItemStack one, ItemStack two){
		return one.getItem() == two.getItem();
	}

}
