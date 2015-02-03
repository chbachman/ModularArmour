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
	 * One of those called methods. Called when the armour is taken off.
	 * @param worldObj
	 * @param player
	 * @param stack2
	 */
	public void onArmourDequip(World worldObj, EntityPlayer player, ItemStack stack);

	/**
	 * Another of those called methods. Called when the armour is put on.
	 * @param worldObj
	 * @param player
	 * @param stack
	 */
	public void onArmourEquip(World worldObj, EntityPlayer player, ItemStack stack);
	
	/**
	 * 
	 * @return whether the current armour piece is calling the {@linkIArmourUpgrade} methods.
	 */
	public boolean isArmour();
	
	/**
	 * Get current capacity/max energy (in RF) of the armour.
	 * @param stack
	 * @return current capacity;
	 */
	public int getCapacity(ItemStack stack);
	
	/**
	 * Set the capacity/max energy (in RF) of the armour.
	 * @param stack
	 * @param amount
	 */
	public void setCapacity(ItemStack stack, int amount);
	
	public int getMaxTransfer(ItemStack stack);
	
	public void setMaxTransfer(ItemStack stack, int amount);
	
	public int getLevel(ItemStack stack);
	
	public ItemStack setLevel(ItemStack stack, int level);
}
