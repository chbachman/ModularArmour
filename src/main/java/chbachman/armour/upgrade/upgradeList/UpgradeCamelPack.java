package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;

public class UpgradeCamelPack extends Upgrade{

	public UpgradeCamelPack() {
		super("camelPack");
	}
	/*
	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {

		EnviroDataTracker tracker = EM_StatusManager.lookupTracker(par3EntityPlayer);
		
		ItemStack plate = trackedEntity.getCurrentItemOrArmor(3);

		if(plate != null)
		{
			if(plate.itemID == EnviroMine.camelPack.itemID)
			{
				if(plate.getItemDamage() < plate.getMaxDamage() && hydration <= 99F - EM_Settings.hydrationMult)
				{
					plate.setItemDamage(plate.getItemDamage() + 1);
					hydrate((float)EM_Settings.hydrationMult);

					if(bodyTemp >= 37F + EM_Settings.tempMult/10F)
					{
						bodyTemp -= EM_Settings.tempMult/10F;
					}
				}
			}
		}
	}
	*/
	
	
}
