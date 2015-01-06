package chbachman.armour.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.IUpgrade;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTUpgradeList;
import cofh.core.item.ItemBase;

public class ItemUpgrade extends ItemBase{

	public ItemUpgrade(String string) {
		super(string);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		//super.addInformation(stack, player, list, bool);
		
		list.add(getUpgrade(stack).getName());
	}
	
	public ItemUpgrade setUpgrade(ItemStack stack, IUpgrade upgrade){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack);
		
		if(!list.isEmpty()){
			list.clear();
		}
		
		list.add(upgrade);
		
		return this;
		
	}
	
	public IUpgrade getUpgrade(ItemStack stack) {
		if(stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack);
		
		if(list.isEmpty()){
			return null;
		}
		
		return list.get(0);
	}

}
