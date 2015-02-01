package chbachman.armour.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import baubles.api.BaublesApi;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;

public class BaublesUtil{

	public static boolean doesPlayerHaveUpgrade(EntityPlayer player, IUpgrade upgrade) {
		ItemStack[] armourArray = player.inventory.armorInventory;

		for (ItemStack armour : armourArray) {
			if(doesItemStackContainUpgrade(armour, upgrade)){
				return true;
			}

		}

		IInventory inventory = BaublesApi.getBaubles(player);

		for(int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack bauble = inventory.getStackInSlot(i);

			if(doesItemStackContainUpgrade(bauble, upgrade)){
				return true;
			}
		}

		return false;
	}

	public static List<ItemStack> getPlayerUpgrades(EntityPlayer player, IUpgrade upgrade){
		List<ItemStack> list = new ArrayList<ItemStack>(6);
		ItemStack[] armourArray = player.inventory.armorInventory;

		for (ItemStack armour : armourArray) {
			if(doesItemStackContainUpgrade(armour, upgrade)){
				list.add(armour);
			}

		}

		IInventory inventory = BaublesApi.getBaubles(player);

		for(int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack bauble = inventory.getStackInSlot(i);

			if(doesItemStackContainUpgrade(bauble, upgrade)){
				list.add(bauble);
			}
		}

		return list;
	}

	public static void removeUpgrade(ItemStack container, IUpgrade upgrade) {

		ItemStack stack = container.copy();

		if (stack.stackTagCompound == null) {
			NBTHelper.createDefaultStackTag(stack);
			return;
		}

		if (stack.getItem() instanceof IModularItem) {

			NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);

			for(int i = 0; i < list.size(); i++){
				if(list.get(i).equals(upgrade)){
					list.remove(i);
					break;
				}
			}

			container.stackTagCompound = stack.stackTagCompound;
		}

	}

	public static List<IUpgrade> getDependencyList(IUpgrade upgrade) {
		List<IUpgrade> list = new ArrayList<IUpgrade>();
		list.add(upgrade);
		return list;
	}

	public static boolean doesItemStackContainUpgrade(ItemStack stack, IUpgrade upgrade) {

		if(stack == null || upgrade == null){
			return false;
		}

		NBTHelper.createDefaultStackTag(stack);

		NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);

		return list.contains(upgrade);
	}

}
