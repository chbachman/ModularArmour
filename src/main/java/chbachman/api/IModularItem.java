package chbachman.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.util.ArmourSlot;
import cofh.api.energy.IEnergyContainerItem;

/**
 * This class has a lot around it that must be implemented. You must call every single method in the {@link IUpgrade} class when appropriate. 
 * This is not optional. This Interface allows you to have some methods called, and also makes sure that upgrades can get into your data when necesary. 
 * @author CBachman
 *
 */
public interface IModularItem extends IEnergyContainerItem{
	
	/**
	 * Gets the slot that the armour contains. See {@link ArmourSlot} for details about the armour numbers that chould be returned. 
	 * @return Slot
	 */
	public int getSlot();

	/**
	 * One of those called methods. Useful for when you want to know when it has been taken off.
	 * @param worldObj
	 * @param player
	 * @param stack2
	 */
	public void onArmorDequip(World worldObj, EntityPlayer player, ItemStack stack);

	public int getCapacity(ItemStack stack);
	
	public void setCapacity(ItemStack stack, int amount);
	
	public int getEnergyPerDamage(ItemStack stack);
	
	public void setEnergyPerDamage(ItemStack stack, int amount);
	
	public int getMaxTransfer(ItemStack stack);
	
	public void setMaxTransfer(ItemStack stack, int amount);
	
	public int getLevel(ItemStack stack);
	
	public ItemStack setLevel(ItemStack stack, int level);
}
