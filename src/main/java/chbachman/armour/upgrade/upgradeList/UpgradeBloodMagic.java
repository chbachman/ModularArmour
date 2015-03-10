package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import chbachman.api.item.IModularItem;
import chbachman.api.upgrade.Upgrade;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;
import cofh.api.energy.IEnergyContainerItem;

public class UpgradeBloodMagic extends Upgrade{

	public UpgradeBloodMagic() {
		super("blood");
	}

	private double cost;

	@Override
	public void registerConfigOptions(){
		cost = ConfigHelper.get(ConfigHelper.SPEED,this, "1LP = ?RF", 1D);
	}
	
	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
		
		if(world.isRemote){
			return 0;
		}
		
		IEnergyContainerItem item = EnergyUtil.getItem(stack);
		IModularItem modularItem = (IModularItem) stack.getItem();
		
		int currentEnergy = item.getEnergyStored(stack);
		int maxEnergy = item.getMaxEnergyStored(stack);
		
		if(currentEnergy == maxEnergy){
			return 0;
		}
		
		SoulNetworkHandler.checkAndSetItemOwner(stack, player);
		
		
		int amount = SoulNetworkHandler.getCurrentEssence(SoulNetworkHandler.getOwnerName(stack));
		
		int toDrain = (int) Math.min(Math.min(amount, (maxEnergy - currentEnergy) / cost), modularItem.getMaxTransfer(stack) / cost);
		
        if (!player.capabilities.isCreativeMode)
        {
            if(SoulNetworkHandler.syphonAndDamageFromNetwork(stack, player, toDrain));
            {
        	   return (int) -(toDrain * cost);
            }
        }
        
        return 0;
		
	}
	
	

}
