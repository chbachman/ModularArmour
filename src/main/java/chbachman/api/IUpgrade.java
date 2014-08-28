package chbachman.api;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
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
	 * Called every tick, when equiped from the armour.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 * @return energy to be subtracted from the armour, each tick.
	 */
	int onArmourTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

	/**
	 * Gets the unique id of the Upgrade, used for saving and loading from NBT.
	 * @return id
	 */
	int getId();

	/**
	 * Called on Armour Equip, also when Player joins world.
	 * @param world
	 * @param player
	 * @param stack
	 * @param armourSlot
	 */
	void onArmourEquip(World world, EntityPlayer player, ItemStack stack, ArmourSlot armourSlot);

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
	void onArmourDequip(World world, EntityPlayer player, ItemStack stack,ArmourSlot armourSlot);

	/**
	 * Gets the Armour Display piece. All of these are added together to get this piece of armour's display value.
	 * @return Armour display, where 1 = half of a armour
	 */
	int getArmourDisplay();

	/**
	 * Called to check what the armour should protect against.
	 * @param player
	 * @param armour
	 * @param source
	 * @param damage
	 * @param armourSlot
	 * @return ArmorProperties describing what the Armour should protect against when this upgrade is equiped. 
	 */
	ArmorProperties getProperties(EntityLivingBase player, ItemStack armour, DamageSource source, double damage, ArmourSlot armourSlot);

	/**
	 * Gets the list of dependencies
	 * @return list of string dependencies.
	 */
	List<String> getDependencies();

}
