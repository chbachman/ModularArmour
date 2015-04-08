package chbachman.api.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public interface ArmourLogic{
	
	//Item
	
	/**
	 * Adds the energy and upgrade lines to the tooltip.
	 * @param list - The tooltip list
	 * @param armour - The ItemStack of the armour;
	 */
	public void addInformation(List<String> list, ItemStack stack);
	
	/**
	 * Called on right click.
	 * @param stack
	 * @param world
	 * @param player
	 * @return stack passed in.
	 */
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player);

	/**
	 * Get the display damage for the ItemStack.
	 * @param stack
	 * @return
	 */
	public int getDisplayDamage(ItemStack stack);
	
	/**
	 * Get the max damage for the ItemStack
	 * @param stack
	 * @return
	 */
	public int getMaxDamage(ItemStack stack);
	
	/**
	 * Get whether the stack is damaged.
	 * @param stack
	 * @return
	 */
	public boolean isDamaged(ItemStack stack);
	
	/**
	 * Get whether the stack is repairable in an anvil.
	 * @param itemToRepair
	 * @param stack
	 * @return
	 */
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack);
	
	/**
	 * Get the Armour Texture for the upgrades.
	 * @param stack
	 * @param entity
	 * @param slot
	 * @param type
	 * @return The location of the texture.
	 */
	public String getArmourTexture(ItemStack stack, Entity entity, int slot, String type);

	/**
	 * Called every tick.
	 * @param world
	 * @param player
	 * @param stack
	 */
	public void onArmourTick(World world, EntityPlayer player, ItemStack stack);
	
	/**
	 * Called on Armour Equip.
	 * @param world
	 * @param player
	 * @param stack
	 */
	public void onArmourEquip(World world, EntityPlayer player, ItemStack stack);

	/**
	 * Called on Armour Dequip.
	 * @param world
	 * @param player
	 * @param stack
	 */
	public void onArmourDequip(World world, EntityPlayer player, ItemStack stack);

	/**
	 * Get the properties for the armour.
	 * @param player
	 * @param stack
	 * @param source
	 * @param damage
	 * @param slot
	 * @return The properties detailing the reaction on hit.
	 */
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack stack, DamageSource source, double damage, int slot);
	
	/**
	 * Get the Armour Display, in half chestplates for the given stack.
	 * @param player
	 * @param stack
	 * @param slot
	 * @return
	 */
	public int getArmourDisplay(EntityPlayer player, ItemStack stack, int slot);

	/**
	 * Called to heal the armour.
	 * @param stack
	 * @param toHeal
	 */
	public abstract void healArmour(ItemStack stack, int toHeal);

	/**
	 * Called to damage the armour.
	 * @param stack
	 * @param damage
	 */
	public abstract void damageArmour(ItemStack stack, int damage);

}
