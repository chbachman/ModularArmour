package chbachman.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.items.armour.logic.UpgradeLogicAdv;

/**
 * The interface that must be implemented for upgrades to work on your armour. You must call every single method in the {@link IUpgrade} class when appropriate. 
 * This is not optional. This interface allows you to have some methods called, and also makes sure that upgrades can get into your data when necesary. 
 * @author CBachman
 *
 */
public interface IModularItem{
	
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
	 * @return whether the current armour piece is calling the {@link chbachman.api.upgrade.IArmourUpgrade} methods.
	 */
	public boolean isArmour();
	
	/**
	 * Called when a upgrade may need to damage outside of returning. Should be the same scale (RF) as the methods.
	 * @param stack
	 * @param damage
	 */
	public void damageArmour(ItemStack stack, int damage);
	
	/**
	 * Called when a upgrade may need to heal damage outside of rendering. Should be the same scale (RF) as the methods.
	 */
	public void healArmour(ItemStack stack, int damage);
	
	/**
	 * Return the Holder instance that the upgrades may use to edit data.
	 */
	public UpgradeLogic getLogic();
	
}
