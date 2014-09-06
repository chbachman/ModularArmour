package chbachman.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.objects.VariableInt;

/**
 * This class has a lot around it that must be implemented. You must call every single method in the {@link IUpgrade} class when appropriate. 
 * This is not optional. This Interface allows you to have some methods called, and also makes sure that upgrades can get into your data when necesary. 
 * @author CBachman
 *
 */
public interface IModularItem{
	
	/**
	 * This one is to let Upgrades edit some data. They pass in the name of the data, and you give them the {@link VariableInt} class.
	 * @param name
	 * @return VariableInt corresponding to the name, if no data is stored, null;
	 */
	public VariableInt getInt(String name);
	
	/**
	 * Gets the slot that the armour contains. See {@link ArmourSlot} for details about the armour numebrs that chould be returned. 
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

}
