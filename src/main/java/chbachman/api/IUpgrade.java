package chbachman.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.util.ArmourSlot;
/**
 * Interface for adding upgrades. Any armour that can hold these must call each of these methods when appropriate.
 * @author chbachman
 *
 */
public interface IUpgrade extends Comparable<IUpgrade>{

	/**
	 * Gets the unique id of the Upgrade, used for saving and loading from NBT.
	 * @return id
	 */
	int getId();

	/**
	 * Gets the Localized information string
	 * @return Localized information string
	 */
	String getInformation();
	
	/**
	 * Get the Localized Name of the Upgrade
	 * @return Localized Name of the Upgrade
	 */
	String getName();

	/**
	 * whether the upgrade is compatible with the given armour type. 
	 * @param armorType
	 * @return
	 */
	boolean isCompatible(IModularItem item, ItemStack stack, int armorType);
	
	/**
	 * This equals method should be done using the ids of the Upgrades.
	 * @param obj
	 * @return equality
	 */
	boolean equals(Object obj);
	
	int hashCode();

	/**
	 * Gets the list of dependencies
	 * @return list of string dependencies.
	 */
	IUpgrade[] getDependencies();
	
	/**
	 * Called every tick, when equiped from the armour.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 * @return energy to be subtracted from the armour, each tick.
	 */
	int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot, int level);
	
	/**
	 * Called when the Upgrade is added to the armour, usually used for editing the VariableInt classes that the object contains. 
	 * @param armour
	 * @param stack
	 */
	void onUpgradeAddition(IModularItem armour, ItemStack stack);
	
	/**
	 * Called on Armour Equip, also when Player joins world.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 */
	void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot, int level);

	/**
	 * Called when the armour is dequiped.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 */
	void onDequip(World world, EntityPlayer player, ItemStack stack,ArmourSlot armourSlot, int level);
	
	/**
	 * checks to see if the upgrade is diabled.
	 * @return whether the upgrade is disabled.
	 */
	boolean isDisabled();
	
	/**
	 * set whether the upgrade is disabled
	 * @param value
	 * @return the Upgrade
	 */
	IUpgrade setDisabled(boolean value);
	

}
