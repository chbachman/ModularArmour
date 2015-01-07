package chbachman.armour.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import chbachman.api.IUpgrade;
import chbachman.armour.util.NBTUpgradeList;
import cofh.core.item.ItemBase;

public class ItemUpgrade extends ItemBase{

	public ItemUpgrade(String string) {
		super(string);
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add(getUpgrade(stack).getName());
	}
	
	public ItemUpgrade setUpgrade(ItemStack stack, IUpgrade upgrade){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound = NBTUpgradeList.getNBTTagCompound(upgrade);
		
		return this;
		
	}
	
	public IUpgrade getUpgrade(ItemStack stack) {
		if(stack.stackTagCompound == null){
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		return NBTUpgradeList.getUpgrade(stack.stackTagCompound);
	}

}
