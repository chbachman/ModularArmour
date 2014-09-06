package chbachman.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.armour.reference.ArmourSlot;

public interface IArmourUpgrade extends IUpgrade{
	
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

}
