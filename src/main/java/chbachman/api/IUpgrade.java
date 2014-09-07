package chbachman.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.armour.reference.ArmourSlot;
/**
 * Interface for adding upgrades. Any armour that can hold these must call each of these methods when appropriate.
 * @author chbachman
 *
 */
public interface IUpgrade {

	/**
	 * Get the Localized Name of the Upgrade
	 * @return Localized Name of the Upgrade
	 */
	String getName();
	
	/**
	 * Gets the Unlocalized Name of the Upgrade
	 * @return Unlocalized Name
	 */
	String getUnlocalizedName();

	/**
	 * Gets the unique id of the Upgrade, used for saving and loading from NBT.
	 * @return id
	 */
	int getId();

	/**
	 * Gets the unlocalized information string
	 * @return unlozalized information string
	 */
	String getInformation();
	
	/**
	 * Can the upgrade be repeated, that is put on more than once. 
	 * @return whether the upgrade can be repeatedly put on. 
	 */
	boolean isRepeatable();

	/**
	 * whether the upgrade is compatible with the given armour type. 
	 * @param armorType
	 * @return
	 */
	boolean isCompatible(int armorType);
	

	/**
	 * Gets the list of dependencies
	 * @return list of string dependencies.
	 */
	List<String> getDependencies();
	
	/**
	 * Called every tick, when equiped from the armour.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 * @return energy to be subtracted from the armour, each tick.
	 */
	int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);
	
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
	void onEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

	/**
	 * Called when the Armour piece runs out of energy.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 */
	void onNoEnergy(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

	/**
	 * Called when the armour is dequiped.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 */
	void onDequip(World world, EntityPlayer player, ItemStack stack,ArmourSlot armourSlot);

}
