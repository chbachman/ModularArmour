package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.EnergyUtil;

public class UpgradeElectrolyzer extends Upgrade{

	public UpgradeElectrolyzer() {
		super("electrolyzer");
	}
	
	@Override
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot, int level) {
        if(player.getAir() <= 90 && EnergyUtil.getEnergyStored(stack) > 1000){
        	
        	player.setAir(300);
        	return ConfigHelper.getEnergyCost(this, "cost for air to be refilled.", 100);
        }
        
        return 0;
    }

}
