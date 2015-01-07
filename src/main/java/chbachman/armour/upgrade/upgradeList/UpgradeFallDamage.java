package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;

public class UpgradeFallDamage extends Upgrade {

	public UpgradeFallDamage() {
		super("fallDamage");
	}

	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
		if (player.fallDistance > 2 && EnergyUtil.getEnergyStored(stack) > 100) {
			player.fallDistance = 0;
			return ConfigHelper.getEnergyCost(this, "cost to fall for every 2 blocks", 100);
		}

		return 0;
	}

}
