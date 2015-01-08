package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;
import cofh.api.energy.IEnergyContainerItem;

public class UpgradeBloodMagic extends Upgrade{

	public UpgradeBloodMagic() {
		super("blood");
	}

	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
		
		if(world.isRemote){
			return 0;
		}
		
		IEnergyContainerItem item = EnergyUtil.getItem(stack);
		
		int currentEnergy = item.getEnergyStored(stack);
		int maxEnergy = item.getMaxEnergyStored(stack);
		
		if(currentEnergy == maxEnergy){
			return 0;
		}
		
		SoulNetworkHandler.checkAndSetItemOwner(stack, player);

        if (!player.capabilities.isCreativeMode)
        {
            if(SoulNetworkHandler.syphonAndDamageFromNetwork(stack, player, maxEnergy - currentEnergy * ConfigHelper.getEnergyCost(this, "LP cost per RF", 1)));
            {
        	   return currentEnergy - maxEnergy; //Oppisite to return negative.
            }
        }
        
        return 0;
		
	}
	
	

}
