package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import chbachman.armour.util.ArmourSlot;

public class UpgradePlayerProtection extends UpgradeProtective{

	public UpgradePlayerProtection() {
		super("playerProtection", .9F);
	}

	@Override
	public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
		Entity entity = source.getSourceOfDamage();

		if(entity == null){
			return false;
		}

		return entity instanceof EntityPlayer;

	}

}
